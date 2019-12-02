package com.king.mpl.Aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@org.aspectj.lang.annotation.Aspect
//@Component
public class StudentInfoServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(StudentInfoServiceAspect.class);

    @Pointcut("execution(private * com.king.mpl.Service.StudentInfoService.*(..))")
    public void pointcut1() {
    }

//    @Before("pointcut1()")
//    public void checkLoginInfo(JoinPoint joinPoint) {
//    }
}
