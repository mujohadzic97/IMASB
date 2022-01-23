package com.amteam.messagingmicroservice.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String requestMethod = request.getMethod();
        String path = request.getRequestURI();
        System.out.println(path+" "+token);
        if (token == null || token.equals("")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "No authorization header or token is present");
            return;
        }
        if(token.startsWith("Bearer "))
            token = token.replace("Bearer ", "");
        if ("/login".equals(path) || "/register-client".equals(path) || "/register-instructor".equals(path)) {
            filterChain.doFilter(request, response);
            return;
        }
        String validateTokenMessage = validateToken(token);
        if (validateTokenMessage != null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), validateTokenMessage);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String validateToken(String token) {
        String extras = jwtTokenUtil.extractSubject(token);
        if (extras.equals("Expired")) {
            return "Token expired, please log in again";
        }
        return null;
    }
}
