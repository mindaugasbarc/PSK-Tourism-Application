package com.tourism.psk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class RequestLogger {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* com.tourism.psk.controller.*.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("request made:" + joinPoint.getSignature());
        for (Object object : joinPoint.getArgs()) {
            logger.info("with argument: " + object );
        }
    }
}
