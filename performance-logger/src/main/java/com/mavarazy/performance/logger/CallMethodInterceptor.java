package com.mavarazy.performance.logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CallMethodInterceptor implements MethodInterceptor {
    final private CallLogService loggerService;

    public CallMethodInterceptor(CallLogService loggerService) {
        this.loggerService = loggerService;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object result = null;
        try {
            loggerService.start(invocation.getMethod(), invocation.getArguments());
            result = invocation.proceed();
            loggerService.end(invocation.getMethod(), result);
        } catch (Throwable throwable) {
            if(throwable instanceof Error) {
                loggerService.error(invocation.getMethod(), (Error) throwable);
            } else {
                loggerService.exception(invocation.getMethod(), throwable);
            }
            throw throwable;
        }
        return result;
    }

}
