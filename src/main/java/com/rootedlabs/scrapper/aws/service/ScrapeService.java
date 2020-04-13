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
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rootedlabs.scrapper.aws.entities.Account;
import com.rootedlabs.scrapper.aws.entities.Scrape;
import com.rootedlabs.scrapper.aws.repo.AccountRepository;
import com.rootedlabs.scrapper.aws.repo.ScrapeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScrapeService {

	@Autowired
	private ScrapeRepository repo;

	@Autowired
	private AccountRepository accountRepo;

	public void create(Scrape scrape) {		
//		if(scrape.getAccount() == null) { TODO
//			throw new Exception();
//		}
		scrape.setStatus(ScrapeStatus.PENDING.name());
		scrape.setCreationTime(LocalDateTime.now());
		repo.save(scrape);
		log.debug("Scrape created {}", scrape);
		
	}
	
	public void update(Scrape scrape) {
		repo.save(scrape);
		log.debug("Scrape updated {}",scrape);
	}
	

	public List<Scrape> findAllByAccount(String accountEmail) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Account account = accountRepo.findByEmail(authentication.getName());
		return repo.findByAccountOrderByCreationTimeDesc(account);
	}
	

	public Scrape findById(Long scrapeId) {
		return repo.findById(scrapeId).get();
	}

	public Optional<Scrape> findTopByAccount(Account account) {
		return repo.findTopByAccountOrderByIdDesc(account);
	}
	
	public static enum ScrapeStatus {
		PENDING,
		FAILED ,
		COMPLETE ;
	}

}
