package com.chen.pandora.starter.web.filter;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author liucheng
 * @since 2018/8/31 上午11:50
 */
@Component
@Log4j2
public class LogFilter implements Filter {

    static final Set<String> ignoreURIs = new HashSet<>();

    static {
        ignoreURIs.add("/favicon.ico");
    }

    static final Set<String> ignoreURIPrefixs = new HashSet<>();

    static {
        ignoreURIPrefixs.add("/static/");
        ignoreURIPrefixs.add("/staticfe/");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            if (needLog(request, response)) {
                StringBuilder logBuff = new StringBuilder()
                        .append(request.getMethod())
                        .append("[")
                        .append(response.getStatus())
                        .append("] - ")
                        .append(request.getRequestURI())
                        .append(",耗时")
                        .append(System.currentTimeMillis() - startTime)
                        .append("ms");
                log.info(logBuff.toString());
            }
        }
    }

    private boolean needLog(HttpServletRequest request, HttpServletResponse response) {
        // 错误直接打印
        if (response.getStatus() != HttpServletResponse.SC_OK) {
            return true;
        }
        String uri = request.getRequestURI();
        // 指定忽略的URL
        if (ignoreURIs.contains(uri)) {
            return false;
        }
        // 指定忽略的URL前缀
        for (String prefix : ignoreURIPrefixs) {
            if (uri.startsWith(prefix)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void destroy() {

    }
}
