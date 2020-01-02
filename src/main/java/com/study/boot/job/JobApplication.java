package com.study.boot.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * @author Xingyu Sun
 */
@Slf4j
@SpringBootApplication
public class JobApplication {

    @Autowired
    private SchedulerFactoryBean factoryBean;

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner() {
        return (args -> {
            log.info("schedule isShutdown ? {}", factoryBean.getScheduler().isShutdown());
            log.info("class is {}",dataSource.getClass());
        });
    }

}
