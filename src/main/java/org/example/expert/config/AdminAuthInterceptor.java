package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Slf4j
public class AdminAuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthInterceptor.class);



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) {
        log.info("<관리자> url : {} , 시간 : {}", request.getRequestURI(), LocalDateTime.now());
        return true;

    }


}
