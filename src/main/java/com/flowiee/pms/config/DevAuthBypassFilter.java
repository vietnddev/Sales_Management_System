package com.flowiee.pms.config;

import com.flowiee.pms.utils.AppConstants;
import com.flowiee.pms.utils.CommonUtils;
import com.flowiee.pms.utils.CoreUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class DevAuthBypassFilter implements Filter {
    private static final boolean mvSystemByPass = true;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            CustomHttpServletRequestWrapper wrappedRequest = new CustomHttpServletRequestWrapper((HttpServletRequest) request);
            String servletPath = wrappedRequest.getServletPath();

            if (WebSecurityConfig.getAuthEndPoint().equals(servletPath)) {
                if (mvSystemByPass) {
                    String username = CoreUtils.trim(wrappedRequest.getParameter("username"));
                    if (CoreUtils.isNullStr(username)) {
                        username = AppConstants.ADMINISTRATOR;
                    }
                    wrappedRequest.setParameter("username", username);
                    wrappedRequest.setParameter("password", CommonUtils.defaultNewPassword);
                }
            }

            chain.doFilter(wrappedRequest, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}