package com.study.boot.job.config;

import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Xingyu Sun
 * @date 2019/12/26 14:20
 */
@Configuration
public class JobConfig {

    @Bean
    public SchedulerFactory schedulerFactory(){
        return new StdSchedulerFactory();
    }
}
