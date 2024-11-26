package com.example.anonymous_board.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
@Slf4j
@Component

public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("필터 진입");
        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(req, res);
        log.info("응답 전 다시 필터");
        var reqJson = new String(req.getContentAsByteArray());
        log.info("req : {}",reqJson);
        var resJson = new String(res.getContentAsByteArray());
        log.info("res : {}",resJson);

        res.copyBodyToResponse();
    }
}
