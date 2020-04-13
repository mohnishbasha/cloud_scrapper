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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private UserDetailsService accountUserDetailsService;
    
    @Autowired
    private TokenUtil tokenUtil;
    
    @Value("${token.http.request.header}")
    private String tokenHeader;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String principal = (String) authentication.getPrincipal();
        String  username = tokenUtil.getUsernameFromToken(principal);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	UserDetails userDetails = this.accountUserDetailsService.loadUserByUsername(username);
        	if (!tokenUtil.validateToken(principal, userDetails)) {
        		throw new BadCredentialsException("The API key was not found "
                        + "or not the expected value.");
        	}
        	authentication.setAuthenticated(true);
        }
        return authentication;
	}

@Override
public boolean supports(Class<?> authentication) {
	log.info("{}",authentication);
	return true;
}

}


