package com.zy.Payroll;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    @Pointcut("execution (* com.zy.Payroll.EmployeeController.*(..))")
    public void log(){
        //System.out.println("logging in log()");
    }
    @Before("log()")
    public void beforeAdvice() {
        System.out.println("beforeAdvice...");
    }
    @After("log()")
    public void afterAdvice() {
        System.out.println("afterAdvice...");
    }
    @Around("log()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("start log: controller function is " + joinPoint.getSignature().getName());
        Object object = joinPoint.proceed();
        System.out.println("end log: controller function is " + joinPoint.getSignature().getName());
        return object;
    }
}
