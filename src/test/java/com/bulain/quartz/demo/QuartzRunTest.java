package com.bulain.quartz.demo;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

public class QuartzRunTest {
    private static final Logger logger = LoggerFactory.getLogger(QuartzSpringTest.class);

    private static Scheduler scheduler;

    @BeforeClass
    public static void setUpClass() throws SchedulerException {
        ClassPathResource resource = new ClassPathResource("quartz.properties");
        SchedulerFactory sf = new StdSchedulerFactory(resource.getPath());
        scheduler = sf.getScheduler();
        scheduler.start();
    }

    @AfterClass
    public static void tearDownClass() throws SchedulerException {
        scheduler.shutdown(true);
    }
    
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
