package com.nuaa.shawn.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*【AOP 面向切面编程】*/
@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //括号里是正则表达式，Before/After 表示符合正则表达式的函数执行 前/后 都会执行一次下面的方法
    @Before("execution(* com.nuaa.shawn.demo.controller.*Controller.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        logger.info("Before method:");
        StringBuilder sb = new StringBuilder();
        //joinPoint.getArgs() 可以获得当前执行函数的参数，我们试着打印出来：
        for (Object obj: joinPoint.getArgs()) {
            sb.append("args: " + obj.toString() + " ");
        }
        logger.info(sb.toString());

    }
    @After("execution(* com.nuaa.shawn.demo.controller.*Controller.*(..))")
    public void afterMethod() {
        logger.info("After method:");

    }
}
