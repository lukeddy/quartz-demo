package com.bulain.quartz.demo;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzDebug {
    private static final Logger logger = LoggerFactory.getLogger(QuartzDebug.class);
    
    public static void printQuartz(Scheduler scheduler) throws SchedulerException {
        logger.debug("printQuartz() - start");

        List<String> jobGroupNames = scheduler.getJobGroupNames();
        for (String groupName : jobGroupNames) {
            GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupEquals(groupName);
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);

            for (JobKey jobKey : jobKeys) {
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                JobDataMap jobDataMap = jobDetail.getJobDataMap();
                logger.info("Job: {}", jobKey);
                Set<String> keySet = jobDataMap.keySet();
                Iterator<String> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Object value = jobDataMap.get(key);
                    logger.info("{} ==> {}", key, value);
                }
            }
        }

        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        for (String groupName : triggerGroupNames) {
            GroupMatcher<TriggerKey> matcher = GroupMatcher.triggerGroupEquals(groupName);
            Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(matcher);
            for (TriggerKey triggerKey : triggerKeys) {
                Trigger trigger = scheduler.getTrigger(triggerKey);
                JobDataMap jobDataMap = trigger.getJobDataMap();
                logger.info("Trigger: {}", triggerKey);
                Set<String> keySet = jobDataMap.keySet();
                Iterator<String> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    Object value = jobDataMap.get(key);
                    logger.info("{} ==> {}", key, value);
                }
            }
        }

        logger.debug("printQuartz() - end");
    }
}
