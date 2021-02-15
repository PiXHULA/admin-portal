package com.joakimatef.demo.filters;


import com.joakimatef.demo.service.security.JpaUserDetailService;
import com.joakimatef.demo.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Implementation of OncePerRequestFilter used in order to
 * use an additional filter in Spring security
 * @author  Joakim Önnhage
 * @version 1.0
 * @since   2021-02-15
 */

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    /*
     * jpaUserDetailService used for CRUD operations for users
     * */
    private final JpaUserDetailService jpaUserDetailService;
    /*
     * JwtUtil used for creating and/or reading jwt token
     * */
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(JpaUserDetailService jpaUserDetailService, JwtUtil jwtUtil) {
        this.jpaUserDetailService = jpaUserDetailService;
        this.jwtUtil = jwtUtil;
    }
    /**
     * Custom filter to use together with JWT and Spring security
     * @param request coming from client
     * @param response send back to the client
     * @param chain filter used in Spring security
     *
     **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.jpaUserDetailService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}

