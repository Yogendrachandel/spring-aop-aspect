package com.learn.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

//@Aspect //Container/class holding AOP logic, Need to add the AOP dependency in pom.xml to use this annotation
//@Component
// This is our logging aspect class; similarly, we can create other aspects such as security, transaction, etc.
public class LoggingAspect {



    /*
    @Before is an advice -Runs before the matched target method.
    The execution -> is Point Cut designator(PCD), but AspectJ has many pointcut designators, such as:
    JoinPoint is a parameter that provides reflective access to both the state available at a join point and static information about it. It allows us to access method signature, arguments, target object, etc.
     */
    @Before("execution(* com.learn.service.OrderService.*(..))") // This is a pointcut expression that run for all methods in OrderService class. The * means any return type, and (..) means any number of arguments.
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(">>> Before Advice: Method " + methodName + " execution started.");
    }

    /*
    Runs after the method finishes, whether it returns normally or throws an exception. It is conceptually similar to finally.
    */
    @After("execution(* com.learn.service.OrderService.*(..))") // This is a pointcut expression that run for all methods in OrderService class. The * means any return type, and (..) means any number of arguments.
    public void logAfterMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(">>> After Advice: Method " + methodName + " executed successfully.");
    }




    /*
    Runs only when the target method completes successfully. It can bind the returned value.
     Important: If the target method throws an exception, @AfterReturning does not execute
     */
    @AfterReturning(pointcut = "execution(* com.learn.service.OrderService.*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println(">>> After Returning Advice: Method " + methodName + " returned: " + result);
    }


    /*
     * @AfterThrowing
     *
     * Runs ONLY when the target method throws an exception.
     *
     * 'throwing = "exception"' binds the thrown exception.
     */
    @AfterThrowing(pointcut = "execution(* com.learn.service.OrderService.failOrder(..))",throwing = "exception")
    public void afterThrowingAdvice( JoinPoint joinPoint,Throwable exception) {
        System.out.println("====== @AfterThrowing =======");
        System.out.println("Method: " + joinPoint.getSignature().getName());
        System.out.println("Exception: " + exception.getMessage());
        System.out.println("=============================");
    }



    /*
     * @Around-> Runs BEFORE and AFTER the target method.
     * The most powerful advice. It can execute code before and after the target method, inspect arguments, inspect/modify the return value, catch exceptions, and decide whether to continue.
     */
    @Around("execution(* com.learn.service.OrderService.failOrder(..))")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=========== @Around: BEFORE target ===========");
        long start = System.currentTimeMillis();
        Object result;

        try {
            // IMPORTANT:
            // This executes the actual service method.
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            System.out.println("=========== @Around caught exception ===========");
            System.out.println("Exception: " + ex.getMessage());
            throw ex; // Re-throw so normal exception flow continues.
        }

        long end = System.currentTimeMillis();

        System.out.println("=========== @Around: AFTER target =============");
        System.out.println("Execution time: " + (end - start) + " ms");
        System.out.println("================================================");
        return result;
    }


    /* Before advice with within() pointcut designator.
     It will match all methods within the OrderService class. */
    @Before("within(com.learn.service.OrderService)")//pointcut expression for within Class , we can use for package also like within(com.learn.service..*) for all classes in the package
    public void logBeforeWithin() {
        System.out.println(">>> Before Advice with within(): Executing before a method in OrderService.");
    }


}





