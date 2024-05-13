package com.flowiee.pms.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.flowiee.pms.controller.category.*.*(..))")
    public void logBeforeCategoryControllerCall(JoinPoint joinPoint) {
        log.info(":::::AOP Before - A method in com.flowiee.pms.controller.category package is about to be called:::::{}", joinPoint);
    }

    @Before("execution(* com.flowiee.pms.controller.product.*.*(..))")
    public void logBeforeProductControllerCall(JoinPoint joinPoint) {
        log.info(":::::AOP Before - A method in com.flowiee.pms.controller.product package is about to be called:::::{}", joinPoint);
    }

    @Before("execution(* com.flowiee.pms.controller.sales.*.*(..))")
    public void logBeforeSalesControllerCall(JoinPoint joinPoint) {
        log.info(":::::AOP Before - A method in com.flowiee.pms.controller.sales package is about to be called:::::{}", joinPoint);
    }

    @Before("execution(* com.flowiee.pms.controller.storage.*.*(..))")
    public void logBeforeStorageControllerCall(JoinPoint joinPoint) {
        log.info(":::::AOP Before - A method in com.flowiee.pms.controller.storage package is about to be called:::::{}", joinPoint);
    }

    @Before("execution(* com.flowiee.pms.controller.system.*.*(..))")
    public void logBeforeSystemControllerCall(JoinPoint joinPoint) {
        log.info(":::::AOP Before - A method in com.flowiee.pms.controller.system package is about to be called:::::{}", joinPoint);
    }
}