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

    //@Before("execution(public * com.flowiee.pms.controller.sales.*ReController.*(..))")
    @Before("execution(* com.flowiee.pms.controller.category.*.*(..))")
    public void logBeforeCategoryControllerCall(JoinPoint joinPoint) throws Throwable {
        log.info(":::::AOP Before category controller call:::::" + joinPoint);
    }
}