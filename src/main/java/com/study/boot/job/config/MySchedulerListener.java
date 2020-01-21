package com.study.boot.job.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * @author Xingyu Sun
 * @date 2020/1/9 10:30
 */
@Slf4j
public class MySchedulerListener implements SchedulerListener {

    @Override
    public void jobScheduled(Trigger trigger) {

    }

    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {

    }

    @Override
    public void triggerFinalized(Trigger trigger) {

    }

    @Override
    public void triggerPaused(TriggerKey triggerKey) {

    }

    @Override
    public void triggersPaused(String s) {

    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {

    }

    @Override
    public void triggersResumed(String s) {

    }

    @Override
    public void jobAdded(JobDetail jobDetail) {

    }

    @Override
    public void jobDeleted(JobKey jobKey) {

    }

    @Override
    public void jobPaused(JobKey jobKey) {

    }

    @Override
    public void jobsPaused(String s) {

    }

    @Override
    public void jobResumed(JobKey jobKey) {

    }

    @Override
    public void jobsResumed(String s) {

    }

    @Override
    public void schedulerError(String s, SchedulerException e) {

    }

    @Override
    public void schedulerInStandbyMode() {

    }

    @Override
    public void schedulerStarted() {
        log.info("schedulerStarted");
    }

    @Override
    public void schedulerStarting() {
        log.info("schedulerStarting");
    }

    @Override
    public void schedulerShutdown() {
        log.info("schedulerShutdown");
    }

    @Override
    public void schedulerShuttingdown() {
        log.info("schedulerShuttingDown");
    }

    @Override
    public void schedulingDataCleared() {

    }
}
