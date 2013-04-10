package com.mavarazy.performance.logger;

import java.lang.reflect.Method;

import com.mavarazy.performance.flow.FlowContext;
import com.mavarazy.performance.flow.FlowSpecification;
import com.mavarazy.performance.flow.call.event.CallEndEvent;
import com.mavarazy.performance.flow.call.event.CallErrorEvent;
import com.mavarazy.performance.flow.call.event.CallExceptionEvent;
import com.mavarazy.performance.flow.call.event.CallStartEvent;

public class CallLogService {

    final private static ThreadLocal<FlowContext> threadContext = new ThreadLocal<FlowContext>();

    final private FlowSpecification callSpecification;

    public CallLogService(FlowSpecification callSpecification) {
        this.callSpecification = callSpecification;
    }

    public void start(Method method, Object[] arguments) {
        getCallContext().process(new CallStartEvent(method, arguments));
    }

    public void error(Method method, Error error) {
        getCallContext().process(new CallErrorEvent(method, error));
    }

    public void exception(Method method, Throwable throwable) {
        getCallContext().process(new CallExceptionEvent(method, throwable));
    }

    public void end(Method method, Object result) {
        getCallContext().process(new CallEndEvent(method, result));
    }

    public FlowContext getCallContext() {
        FlowContext callContext = threadContext.get();
        // Check current call value
        if (callContext == null) {
            callContext = FlowContext.create(callSpecification);
            // If call exists add a subCall
            threadContext.set(callContext);
        }
        return callContext;
    }
}
