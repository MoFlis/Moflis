package com.project.moflis.global.security.filter;

import com.project.moflis.global.security.model.CustomUserDetails;
import jakarta.servlet.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

public class DummyLoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        CustomUserDetails fakeUser = new CustomUserDetails(3, "devUser");
        var auth = new UsernamePasswordAuthenticationToken(fakeUser, null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);
    }
}