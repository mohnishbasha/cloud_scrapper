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

package com.rootedlabs.scrapper.aws.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import com.rootedlabs.scrapper.aws.controller.model.MetaDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rootedlabs.scrapper.aws.entities.Account;
import com.rootedlabs.scrapper.aws.entities.EC2;
import com.rootedlabs.scrapper.aws.repo.AccountRepository;
import com.rootedlabs.scrapper.aws.service.EC2Service;
import com.rootedlabs.scrapper.aws.service.ScrapeService;

@RestController
public class EC2Controller extends AbstractController {

	@Autowired
	private EC2Service ec2Service;

	@Autowired
	private ScrapeService scrapeService;

	@Autowired
	private AccountRepository accountRepo;

	@GetMapping("/ec2s")
	public ResponseEntity<MetaDataModel<List<EC2>>> getAllEC2s(Principal principal) {
		Account account = accountRepo.findByEmail(principal.getName());
		final List<EC2> instances = new ArrayList<>();
		scrapeService.findTopByAccount(account).ifPresent(scrape -> {
			instances.addAll(ec2Service.findAllByScrape(scrape.getId()));
		});

		return ok(instances);
	}

	@GetMapping("/scrape/{scrapeId}/ec2s")
	public ResponseEntity<MetaDataModel<List<EC2>>> getAllEC2sByScrape(@PathVariable Long scrapeId,
			Principal principal) {
		Account account = accountRepo.findByEmail(principal.getName());
		final List<EC2> instances = new ArrayList<>();
		if (scrapeId == 0) {
			scrapeService.findTopByAccount(account).ifPresent(scrape -> {
				instances.addAll(ec2Service.findAllByScrape(scrape.getId()));
			});
		} else {
			instances.addAll(ec2Service.findAllByScrape(scrapeId));
		}

		return ok(instances);
	}

}
