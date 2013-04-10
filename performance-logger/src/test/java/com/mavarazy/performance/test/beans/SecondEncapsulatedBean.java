package com.mavarazy.performance.test.beans;

import org.springframework.beans.factory.annotation.Autowired;

public class SecondEncapsulatedBean {

    @Autowired
    private EncapsulatedBean encapsulatedBean;
    
    public void secondEncapsulationCall(int numSecondLevelCalls, int numTherdLevelCalls) {
        for(int i = 0; i < numSecondLevelCalls; i++)
            encapsulatedBean.encapsulatedCall(numTherdLevelCalls);
    }
}
