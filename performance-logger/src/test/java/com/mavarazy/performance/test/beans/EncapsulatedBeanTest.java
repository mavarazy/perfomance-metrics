package com.mavarazy.performance.test.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/simple-bean-logger.xml" })
public class EncapsulatedBeanTest {

    @Autowired
    private EncapsulatedBean encapsulatedBean;

    @Test
    public void simpleCall() {
        encapsulatedBean.encapsulatedCall(5);
    }
}
