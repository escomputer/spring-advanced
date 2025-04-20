package org.example.expert.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminApiAspect {

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..)) ||" +
    "execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable{
    }
}
