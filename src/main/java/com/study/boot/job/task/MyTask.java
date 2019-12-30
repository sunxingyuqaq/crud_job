package com.study.boot.job.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

/**
 * @author Xingyu Sun
 * @date 2019/12/26 14:14
 */
@Slf4j
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class MyTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("MyTask--------------------started");
        log.info("key is {}-----now is 【{}】", LocalDateTime.now(), jobExecutionContext.getJobDetail().getKey());
    }
}
