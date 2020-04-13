/*******************************************************************************
 The MIT License

 Copyright (c) 2020 Mohinish (aka. rootedlabs), Siddharth (aka. spaul)

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.

 ******************************************************************************/

package com.rootedlabs.scrapper.aws.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.rootedlabs.scrapper.aws.entities.EC2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.rootedlabs.scrapper.aws.entities.Account;
import com.rootedlabs.scrapper.aws.entities.Scrape;
import com.rootedlabs.scrapper.aws.repo.EC2Repository;

import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesRequest;
import software.amazon.awssdk.services.ec2.model.DescribeInstancesResponse;
import software.amazon.awssdk.services.ec2.model.Instance;
import software.amazon.awssdk.services.ec2.model.Reservation;

@Service
@Slf4j
public class EC2Service {

	@Autowired
	private EC2Repository repo;

	@Autowired
	private ScrapeService scrapeService;

	private ExecutorService executor = Executors.newFixedThreadPool(10);

	@Async
	public void scrapeResources(Account account, Scrape scrape) {
		
//		AwsCredentials credentials = AwsBasicCredentials.create("AKIAUW3AYKLYIA2KRIEV",
//				"1Jx2l+Ivfn76s69OYjki38Di2hYnQr+68Q3SE/2E");
		AwsCredentials credentials = AwsBasicCredentials.create(account.getAccessKey(), account.getSecretAccessKey());
		AwsCredentialsProvider credentialsProvider = StaticCredentialsProvider.create(credentials);
		List<EC2> models = Collections.synchronizedList(new ArrayList<>());

		Region.regions().stream().filter(r -> !r.isGlobalRegion()).forEach(region -> {
			executor.execute(() -> {
				log.info("Retriving instance details for region {}", region);
				try {
					models.addAll(pullEC2DetailsPerRegion(credentialsProvider, region, scrape));
					scrape.setStatus(ScrapeService.ScrapeStatus.COMPLETE.name());
				} catch (Exception e) {
					log.error("Error retrieving details for region {}. error {}", region, e.getMessage());
					scrape.setStatus(ScrapeService.ScrapeStatus.FAILED.name());
				}
			});
		});
		executor.shutdown();
		try {
			executor.awaitTermination(5, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("list {}", models);
		scrapeService.update(scrape);
		repo.saveAll(models);
	}

	

	@Async
	private List<EC2> pullEC2DetailsPerRegion(AwsCredentialsProvider credentialsProvider, Region region, Scrape scrape) {
		List<EC2> result = new ArrayList<>();
		String nextToken = null;
		do {
			Ec2Client ec2 = Ec2Client.builder().credentialsProvider(credentialsProvider).region(region).build();
			DescribeInstancesRequest request = DescribeInstancesRequest.builder().nextToken(nextToken).build();
			DescribeInstancesResponse response = ec2.describeInstances(request);

			for (Reservation reservation : response.reservations()) {
				for (Instance instance : reservation.instances()) {
					EC2 ec2Instance = new EC2();
					ec2Instance.setInstanceId(instance.instanceId());
					ec2Instance.setInstanceType(instance.instanceTypeAsString());
					ec2Instance.setState(instance.state().nameAsString());
					ec2Instance.setPublicDNSName(instance.publicDnsName());
					ec2Instance.setPrivateDNSName(instance.privateDnsName());
					ec2Instance.setPublicIpAddress(instance.publicIpAddress());
					ec2Instance.setPrivateIpAddress(instance.privateIpAddress());
					ec2Instance.setRegion(region.id());
					ec2Instance.setOwnerId(reservation.ownerId());
					ec2Instance.setCoreCount(instance.cpuOptions().coreCount().toString());
					ec2Instance.setTags(String.join(",", instance.tags().stream().map(t ->t.key()+"="+t.value()).collect(Collectors.toList())));
					ec2Instance.setGroups(String.join(",",instance.securityGroups().stream().map(g -> g.groupName()).collect(Collectors.toList())));
					ec2Instance.setSubnetId(instance.subnetId());
					ec2Instance.setKeyName(instance.keyName());
					ec2Instance.setLaunchTime(LocalDateTime.ofInstant(instance.launchTime(),ZoneId.systemDefault()));
					
					ec2Instance.setScrape(scrape);
					result.add(ec2Instance);
				}
			}
			nextToken = response.nextToken();
		} while (nextToken != null);
		log.debug("Instances count for region {} is {}", region, result.size());
		return result;
	}


	public List<EC2> findAllByScrape(Long scrapeId) {
		Scrape scrape = scrapeService.findById(scrapeId);	
		List<EC2> instances = repo.findByScrape(scrape);
		return instances;
	}
}
