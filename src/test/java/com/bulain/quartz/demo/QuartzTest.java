package com.bulain.quartz.demo;

import java.text.ParseException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;
import org.springframework.core.io.ClassPathResource;

public class QuartzTest {
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
    public void run() throws SchedulerException, ParseException {
        JobKey jobKey1 = JobKey.jobKey("job1", "group1");
        JobKey jobKey2 = JobKey.jobKey("job2", "group2");

        schedule(scheduler, jobKey1, 2);
        schedule(scheduler, jobKey2, 5);

        try {
            Thread.sleep(5 * 1000L);
        } catch (Exception e) {
        }

        unschedule(scheduler, jobKey1);
        unschedule(scheduler, jobKey2);
    }

    private void schedule(Scheduler sched, JobKey jobKey, int triggerCnt) throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(QuartzJob.class).withIdentity(jobKey);
        JobDetail job = jobBuilder.build();
        job.getJobDataMap().put("jkey", "jvalue");
        job.getJobDataMap().put("jInt", "1");
        sched.addJob(job, true);

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?")
                .withMisfireHandlingInstructionFireAndProceed();

        for (int i = 0; i < triggerCnt; i++) {
            MutableTrigger trigger = cronScheduleBuilder.build();
            trigger.setKey(TriggerKey.triggerKey("trigger" + (i + 1), jobKey.getGroup()));
            trigger.setJobKey(job.getKey());
            trigger.getJobDataMap().put("tkey", "tvalue");
            sched.scheduleJob(trigger);
        }
    }

    private void unschedule(Scheduler sched, JobKey jobKey) throws SchedulerException {
        sched.deleteJob(jobKey);
    }

}
