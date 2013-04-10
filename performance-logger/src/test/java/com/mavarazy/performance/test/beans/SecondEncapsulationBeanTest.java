package com.mavarazy.performance.test.beans;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/simple-bean-logger.xml"})
public class SecondEncapsulationBeanTest {

    @Autowired
    private SecondEncapsulatedBean secondEncapsulatedBean;

    @Test
    public void testLoggerValue() {
        secondEncapsulatedBean.secondEncapsulationCall(1, 1);
    }
}
