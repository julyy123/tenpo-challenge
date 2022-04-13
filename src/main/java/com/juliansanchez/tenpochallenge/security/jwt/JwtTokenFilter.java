package com.juliansanchez.tenpochallenge.security.jwt;

import com.juliansanchez.tenpochallenge.security.user.CustomUserDetailsService;
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
 * Jwt token filter class
 */
@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private JwtTokenUtil jwtTokenUtil;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            String tokenHeader = request.getHeader("Authorization");
            String username = null;
            String token = null;

            if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
                token = tokenHeader.substring(7);
                username = jwtTokenUtil.getSubject(token);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        } catch (Exception e) {
            log.error("Cannot set user authentication: {}", e);
        }

        chain.doFilter(request, response);
    }
}
