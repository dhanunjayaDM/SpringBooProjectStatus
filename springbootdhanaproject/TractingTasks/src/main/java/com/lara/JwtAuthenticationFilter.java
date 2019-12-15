package com.lara;

import java.io.IOException;


import javax.servlet.FilterChain;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.lara.model.Users;
import com.lara.service.UserDetailsServiceImpl;
import com.lara.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
/*
 * for every request application will call this filter
 * 
 */

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter 
{
	@Autowired
    private TokenProvider jwtTokenUtil;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private UserService userService;


	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader("Authorization");
		String username = null;
        String authToken = null;
        if (header != null && header.startsWith("Bearer ")) {
            authToken = header.replace("Bearer ","");
            try {
            	System.out.println(authToken);
            	/*
            	 * from jwt token geting username
            	 */
            	
            	
                username = jwtTokenUtil.getUsernameFromToken(authToken);
                System.out.println(username);
                
            } catch (IllegalArgumentException e) {
                logger.error("an error occured during getting username from token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("the token is expired and not valid anymore", e);
            } catch(SignatureException e){
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        }
        else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	
        	Users user=userService.getUserByName(username);
        	UserPrincipal principal = new UserPrincipal(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            System.out.println("userDetailsService filter");
            System.out.println(userDetails);

            if (jwtTokenUtil.validateToken(authToken, principal)) {
            	System.out.println(authToken);
                UsernamePasswordAuthenticationToken authentication = jwtTokenUtil.getAuthentication(authToken, SecurityContextHolder.getContext().getAuthentication(), principal);
                System.out.println(" authentication  ");
                System.out.println(authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
	}
	
}
