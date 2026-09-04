
package com.c2.lc.lib.filters;

import com.c2.lc.lib.filters.interfaces.ApiFilterService;
import com.c2.lc.lib.utils.SystemHelper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class RequestFilter implements Filter {

    @Autowired private ApiFilterService apiFilterService;
    @Autowired private SystemHelper helper;

    @Value("${api.doFilter:false}") private boolean doFilter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        RequestWrapper wrappedRequest = new RequestWrapper((HttpServletRequest) request, apiFilterService, helper, doFilter);
        chain.doFilter(wrappedRequest, response);
    }

    @Override
    public void destroy() {
    }

}
