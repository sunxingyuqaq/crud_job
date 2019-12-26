package com.study.boot.job.task;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

/**
 * @author Xingyu Sun
 * @date 2019/12/23 14:59
 */
@Slf4j
@DisallowConcurrentExecution
public class MyJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("MyJob--------------------started");
        log.info("key is {}-----now is 【{}】", LocalDateTime.now(), jobExecutionContext.getJobDetail().getKey());
    }
}
