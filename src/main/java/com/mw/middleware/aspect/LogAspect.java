package com.mw.middleware.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    // logger
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(* com.mw.middleware.controller.*.*(..))")
    public void logPointcut() {}

    @Before("logPointcut()")
    public void beforeLog(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        String logMsg = String.format("调用方法：%s.%s，参数：%s", className, methodName, Arrays.toString(args));
        logger.info(logMsg);
    }

    @AfterReturning(value = "logPointcut()", returning = "result")
    public void afterReturningLog(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        String logMsg = String.format("调用方法：%s.%s，返回值：%s", className, methodName, result);
        logger.info(logMsg);
    }
}
