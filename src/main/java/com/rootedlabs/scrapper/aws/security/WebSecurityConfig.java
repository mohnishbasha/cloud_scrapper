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

package com.rootedlabs.scrapper.aws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig{
	
	
	@Configuration
	@Order(1)
	public static class APISecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private TokenUnAuthorizedResponseAuthenticationEntryPoint tokenUnAuthorizedResponseAuthenticationEntryPoint;

//		@Autowired
//		private TokenAuthenticationProvider tokenAuthenticationProvider;
		
		@Autowired
		private TokenAuthenticationFilter tokenAuthenticationFilter;

		@Value("${token.get.uri}")
		private String authenticationPath;

		@Override
		protected void configure(final HttpSecurity http) throws Exception {
			http.exceptionHandling().authenticationEntryPoint(tokenUnAuthorizedResponseAuthenticationEntryPoint);//
			http.cors().disable().csrf().disable()//
					.antMatcher("/api/**").authorizeRequests().anyRequest().authenticated().and()//
			.addFilterAfter(tokenAuthenticationFilter, AbstractPreAuthenticatedProcessingFilter.class);
			http.sessionManagement().disable();
		}		

		@Override
	    public void configure(WebSecurity web) throws Exception {
	        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").and()//
	        .ignoring().antMatchers(HttpMethod.POST, "/authenticate");
	    }
	
	}

	@Order(2)
	@Configuration
	public static class UIWebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Autowired
		private UserDetailsService accountDetailsService;
		
		@Autowired
		private LoginSuccessHandler loginSuccessHandler;

		@Override
		protected void configure(final HttpSecurity http) throws Exception {
			http.authorizeRequests().antMatchers("/css/**", "/js/**", "/images/**", "*/favicon.ico", "/login",
					"/login-error", "/index", "/", "/register", "/submit-registration", "/perform-login").permitAll()//
			.and().authorizeRequests().antMatchers("/ui/**").authenticated()//
			.and().formLogin().loginPage("/").loginProcessingUrl("/perform-login")//
					.usernameParameter("email").passwordParameter("password")//
					.successHandler(loginSuccessHandler).failureUrl("/login");//

			http.sessionManagement().invalidSessionUrl("/").maximumSessions(1).expiredUrl("/").and().sessionFixation()
					.newSession();
		}

		@Autowired
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(accountDetailsService).passwordEncoder(passwordEncoderBean());
		}

		@Bean
		public BCryptPasswordEncoder passwordEncoderBean() {
			return new BCryptPasswordEncoder();
		}

		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
}
