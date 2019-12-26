package com.study.boot.job;

import com.study.boot.job.utils.JobUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Xingyu Sun
 */
@SpringBootApplication
public class JobApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
        /// JobUtils.addJob("测试定时任务","test","测试定时任务","testTrigger", MyJob.class,"0/5 * * * * ?");
        JobUtils.startJobs();
    }

}
