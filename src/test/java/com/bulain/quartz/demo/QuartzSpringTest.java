package com.bulain.quartz.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml"})
public class QuartzSpringTest {
    private static final Logger logger = LoggerFactory.getLogger(QuartzSpringTest.class);

    @Autowired
    private Scheduler scheduler;

    @Test
    public void testQuartz() throws SchedulerException {
        logger.debug("testQuartz() - start");

        QuartzDebug.printQuartz(scheduler);
        
        try {
            Thread.sleep(5 * 1000L);
        } catch (Exception e) {
        }

        logger.debug("testQuartz() - end");
    }
}
