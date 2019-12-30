package com.study.boot.job;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author Xingyu Sun
 */
@SpringBootApplication
public class JobApplication {

    @Autowired
    private SchedulerFactoryBean quartzScheduler;

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (args -> {
            Scheduler scheduler = quartzScheduler.getScheduler();
            scheduler.start();
        });
    }

}
