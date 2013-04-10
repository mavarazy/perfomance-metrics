package com.mavarazy.performance.logger;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring/call-logger.xml"})
public class InitializationTest {

    @Autowired
    private SpringPerformanceLogger springPerformanceLogger;
    
    @Test
    public void testStarted() {
        Assert.assertNotNull(springPerformanceLogger);
    }
}
