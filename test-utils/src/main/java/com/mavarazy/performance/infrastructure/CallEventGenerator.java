package com.mavarazy.performance.infrastructure;


import java.util.Random;

import com.mavarazy.performance.Invocation;
import com.mavarazy.performance.flow.call.CallEvent;
import com.mavarazy.performance.flow.call.event.CallEndEvent;
import com.mavarazy.performance.flow.call.event.CallErrorEvent;
import com.mavarazy.performance.flow.call.event.CallExceptionEvent;
import com.mavarazy.performance.flow.call.event.CallStartEvent;

public class CallEventGenerator {

    final static private Invocation DEFAULT_INVOCATION = Invocation.valueOf("TestClass", "testMethod");
    final static private String DEFAULT_ERROR_MESSAGE = "Error message";
    final static private String DEFAULT_EXCEPTION_MESSAGE = "Exception message";

    final static private Random random = new Random();

    public static CallEvent random() {
        int selection = random.nextInt(4);
        switch (selection) {
        case 0:
            return generateCallStartedEvent(DEFAULT_INVOCATION);
        case 1:
            return generateCallEndEvent(DEFAULT_INVOCATION);
        case 2:
            return generateCallEndEvent(DEFAULT_INVOCATION);
        case 3:
            return generateCallExceptionEvent(DEFAULT_INVOCATION);
        }
        return null;
    }

    public static CallStartEvent generateCallStartedEvent(Invocation invocation) {
        return new CallStartEvent(invocation);
    }

    public static CallEndEvent generateCallEndEvent(Invocation invocation) {
        return new CallEndEvent(invocation);
    }
    
    public static CallErrorEvent generateCallErrorEvent(Invocation invocation) {
        return new CallErrorEvent(invocation, DEFAULT_ERROR_MESSAGE);
    }
    
    public static CallExceptionEvent generateCallExceptionEvent(Invocation invocation) {
        return new CallExceptionEvent(invocation, DEFAULT_EXCEPTION_MESSAGE);
    }
}
