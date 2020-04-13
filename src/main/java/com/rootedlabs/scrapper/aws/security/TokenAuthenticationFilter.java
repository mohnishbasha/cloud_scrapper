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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private UserDetailsService accountUserDetailsService;
    
    @Autowired
    private TokenUtil tokenUtil;
    
    @Value("${token.http.request.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("Authentication Request For '{}'", request.getRequestURL());

        final String requestTokenHeader = request.getHeader(this.tokenHeader);

        String username = null;
        if (Strings.hasText(requestTokenHeader)) {
            try {
                username = tokenUtil.getUsernameFromToken(requestTokenHeader);
            } catch (IllegalArgumentException e) {
                log.error("TOKEN_UNABLE_TO_GET_USERNAME", e);
            } catch (ExpiredJwtException | MalformedJwtException e) {
                logger.warn("TOKEN_EXPIRED", e);
            }
        } else {
            log.warn("TOKEN_HEADER_NOT_PRESENT "+request.getRequestURL());
        }

        log.trace("TOKEN_USERNAME_VALUE '{}'", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.accountUserDetailsService.loadUserByUsername(username);

            if (requestTokenHeader!=null && tokenUtil.validateToken(requestTokenHeader, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        chain.doFilter(request, response);
    }



}


