package com.cts.example.UserApplication.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpRs = (HttpServletResponse) servletResponse;

        String authHeader = httpRq.getHeader("authorization");

        if(authHeader ==null || !authHeader.startsWith("Bearer")) {
            throw new ServletException("Missing or invalid authorization header");
        }

        String jwtToken = authHeader.substring(7);

        Claims claims = Jwts.parser().setSigningKey("mySecretKey").parseClaimsJws(jwtToken).getBody();

        httpRq.setAttribute("username", claims);

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
