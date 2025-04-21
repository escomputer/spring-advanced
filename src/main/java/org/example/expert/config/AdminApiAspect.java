package org.example.expert.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminApiAspect {

    private final ObjectMapper objectMapper;

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.*(..)) ||" +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.*(..))")
    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {

        /**
         * HttpServletRequest 를 받아오고, 사용자 정보를 추출한다.
         */
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request =((ServletRequestAttributes) requestAttributes).getRequest();
        Long userId =(Long) request.getAttribute("userId");
        String url = request.getRequestURI();
        LocalDateTime now = LocalDateTime.now();


        String requestBody ;

        try{
            Object[] args = joinPoint.getArgs();
            Map<String,Object> paramMap=new HashMap<>();        // json 형태 ex) "message" : "안녕하세요"
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            String[] parameterNames = methodSignature.getParameterNames();
            for (int i =0;i<args.length;i++){
                paramMap.put(parameterNames[i],args[i]);        //paramMap.put("message", "안녕하세요");
            }

            requestBody = objectMapper.writeValueAsString(paramMap);

        }catch (Exception e){
            log.warn("요청을 제대로 하지 못하는 상태입니다.",e);
            requestBody ="null";
        }

        /**
         * API 실행
         */
        Object result = joinPoint.proceed();

        String responseBody;

        try{
            responseBody=objectMapper.writeValueAsString(result);
        }catch (Exception e){
            log.warn("응답을 제대로 하지 못하는 상태입니다.");
            responseBody="null";
        }


        /**
         * 요청이나 응답에 일부 실패해도 일단 로그는 남겨짐
         * 그 경우에는 null 값이 입력 될 예정
         */
        log.info("[ADMIN LOG]\n 요청자 ID : {} \n 요청 URL : {} \n 요청 시각 : {} \n 요청 본문 : {} \n 응답 본문 : {}",userId,url,now,requestBody,responseBody);

        return result;
        }
}
