package com.ccnet.core.common.utils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 * 过滤掉url后面的jsessionid
 * @author JackWang
 *
 */
public class UrlFilter implements Filter{    
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    	if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (httpRequest.isRequestedSessionIdFromURL()) {
            HttpSession session = httpRequest.getSession();
            if (session != null) session.invalidate();
        }

        HttpServletResponseWrapper wrappedResponse = new HttpServletResponseWrapper(httpResponse) {
            @Override
            public String encodeRedirectUrl(String url) {
                return url;
            }

            @Override
            public String encodeRedirectURL(String url) {
                return url;
            }

            @Override
            public String encodeUrl(String url) {
                return url;
            }

            @Override
            public String encodeURL(String url) {
                return url;
            }
        };

        chain.doFilter(request, wrappedResponse);
    }

    public void init(FilterConfig config) throws ServletException {
    	
    }

    public void destroy() {
    	
    }
  }
