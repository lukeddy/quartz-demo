package com.bulain.quartz.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class QuartzJob implements Job {
    private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Trigger trigger = context.getTrigger();

        JobKey jobkey = jobDetail.getKey();
        TriggerKey triggerKey = trigger.getKey();
        Long threadId = Thread.currentThread().getId();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String fireTime = df.format(context.getFireTime());
        String scheduledFireTime = df.format(context.getScheduledFireTime());
        Object jvalue = context.getMergedJobDataMap().get("jkey");
        Object tvalue = context.getMergedJobDataMap().get("tkey");
        int cnt = jobDetail.getJobDataMap().getIntFromString("jInt");

        logger.info("execute: {} - {} @Thread-{} @{}==>{} - {} {} {}", new Object[]{jobkey, triggerKey, threadId,
                scheduledFireTime, fireTime, jvalue, tvalue, cnt});

        jobDetail.getJobDataMap().putAsString("jInt", ++cnt);
    }

}
