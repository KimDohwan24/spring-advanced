package org.example.expert.domain.common.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AopAspect {


    // @Pointcut 없이 작성함
    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")

    public Object logAdminApi(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr.getRequest();

        // 저장된 정보 꺼내오기
        String url = request.getRequestURI();
        String method = request.getMethod();
        LocalDateTime now = LocalDateTime.now();
        Object userId = request.getSession().getAttribute("userId");

        String requestData = Arrays.toString(joinPoint.getArgs());
        log.info("id={}, time={}, method={}, url={}, data={}", userId, now, method, url, requestData);

        // 매서드 실행
        Object result = joinPoint.proceed();

        String responseData = result != null ? result.toString() : "null";
        log.info("url={}, response={}",  url, responseData);

        return result;
    }

}



