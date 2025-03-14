package com.coursemaster.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.coursemaster.service.impl.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Вызван метод с именем{}", joinPoint.getSignature().toShortString());
        log.info("С аргументами {}", Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "execution(* com.coursemaster.service.impl.*.*(..))", returning = "result")
    public void logAfter(JoinPoint joinPoint, Object result) {
        log.info("Метод {} успешно выполнен.", joinPoint.getSignature().toShortString());
        log.info("Результат: {}", result);
    }

    @AfterThrowing(pointcut = "execution(* com.coursemaster.service.impl.*.*(..))", throwing = "throwable")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable throwable) {
        log.error("Ошибка в методе {}. Сообщение: {}", joinPoint.getSignature().toShortString(), throwable.getMessage(), throwable);
    }
}