package org.example.expert.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Slf4j
public class AdminAuthInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(AdminAuthInterceptor.class);

    /**
     *로그인 하고 세션에 JSESSIONID가 저장되어있으면  클라이언트 상태가 유지되고
     * 세션에서 userRole이 관리자 (admin,Admin,aDmin ~~,,,)일때
     * 관리자임을 인증할 수 있다.
     *
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object Handler) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;

        }

        String userRole = (String) session.getAttribute("user-role");

        if (!"admin".equalsIgnoreCase(userRole)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        log.info("<관리자> url : {} , 시간 : {}", request.getRequestURI(), LocalDateTime.now());
        return true;

    }


}
