package com.mavarazy.performance.test.beans;

import org.springframework.beans.factory.annotation.Autowired;

public class EncapsulatedBean {

    @Autowired
    private SimpleBean singleBean;
    
    public void encapsulatedCall(int numTimes){
        for(int i = 0; i < numTimes; i++)
            singleBean.doNothing();
    }
}
