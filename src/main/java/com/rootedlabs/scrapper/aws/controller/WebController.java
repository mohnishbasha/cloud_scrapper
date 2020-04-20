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

import java.net.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.rootedlabs.scrapper.aws.entities.Account;
import com.rootedlabs.scrapper.aws.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 */

@Controller
@Slf4j
public class WebController {

	@Autowired
	private AccountService accountService;

	@GetMapping(value = { "/", "/index" })
	public String index() {
		SecurityContextHolder.createEmptyContext();
		return "index";
	}

	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	@GetMapping("/login-error")
	public String loginError(Model model) {
		model.addAttribute("loginError", true);
		return "login";
	}

	@GetMapping(value = {"/ui/dashboard","/ui/scrape/**",})
	public String dashboard() {
		return "forward:/ui/index.html";
	}

	@GetMapping(value = "/register")
	public String showRegistrationForm(WebRequest request, Model model) {
		Account account = new Account();
		model.addAttribute("userDto", account);
		return "register";
	}

	@PostMapping(value = "/submit-registration")
	public ModelAndView saveUser(ModelAndView modelAndView, @ModelAttribute("userDto") @Valid final Account account,
			BindingResult bindingResult, HttpServletRequest request, Errors errors) {

		Account emailExists = accountService.findByEmail(account.getEmail());

		if (emailExists != null) {
			modelAndView.setViewName("register");
			bindingResult.rejectValue("email", "emailAlreadyExists");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register");
		} else {
			try {
				accountService.createNewAccount(account);
				modelAndView.addObject("confirmationMessage", "Registration successful. Login.");
			} catch (Exception e) {
				bindingResult.rejectValue("email", "keyAlreadyExists");
			}
			modelAndView.addObject("userDto", new Account());
			modelAndView.setViewName("register");
		}

		return modelAndView;
	}

	@GetMapping("/error ")
	public String errorPage(HttpRequest req) {
		log.debug("{}", req);
		return "register";
	}
}
