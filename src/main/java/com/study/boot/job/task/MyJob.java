package com.study.boot.job.task;

import cn.hutool.json.JSONUtil;
import com.study.boot.job.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @author Xingyu Sun
 * @date 2019/12/23 14:59
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJob implements Job {

    @Autowired
    private UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            log.info("MyJob--------------------started");
            JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
            log.info("dataMap is 【{}】-----now is 【{}】", JSONUtil.toJsonStr(dataMap), LocalDateTime.now());
            userService.say();
            log.info("MyJob--------------------end");
        } catch (Exception e) {
            log.error("my-job error", e);
            JobExecutionException jobExecutionException = new JobExecutionException(e);
            jobExecutionException.setRefireImmediately(true);
        }
    }

}
