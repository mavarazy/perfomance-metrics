package com.mavarazy.performance.infrastructure.presentation;

import junit.framework.Assert;

import org.junit.Test;

import com.mavarazy.performance.flow.event.FlowEvent;
import com.mavarazy.performance.infrastructure.FlowEventGenerator;
import com.mavarazy.performance.infrastructure.PresentationStream;

abstract public class AbstractFlowEventPresentationStreamTest<R> {

    protected PresentationStream<R, FlowEvent> presentationStream;

    protected AbstractFlowEventPresentationStreamTest(PresentationStream<R, FlowEvent> presentationStream) {
        this.presentationStream = presentationStream;
    }

    @Test
    public void testConvertion() {
        FlowEvent originalEvent = FlowEventGenerator.random();
        R presentation = presentationStream.write(originalEvent);
        FlowEvent deserializedEvent = presentationStream.read(presentation);
        Assert.assertEquals(deserializedEvent, originalEvent);
    }

}
