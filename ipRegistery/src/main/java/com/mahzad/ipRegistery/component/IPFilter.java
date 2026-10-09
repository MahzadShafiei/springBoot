package com.mahzad.ipRegistery.component;

import com.mahzad.ipRegistery.service.IPService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IPFilter implements Filter {

    private final IPService ipService;

    public IPFilter(IPService ipService) {
        this.ipService = ipService;
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        ipService.registerIP(httpRequest);

        chain.doFilter(request, response);
    }
}
