package com.mw.middleware.aspect;

import com.mw.middleware.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionAspect {
    @Around("execution(* com.mw.middleware.controller.*.*(..))")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (RuntimeException e) {
            throw new BusinessException("发生业务异常：" + e.getMessage());
        }
        return result;
    }
}
