package com.study.boot.job.config;

import cn.hutool.core.lang.Assert;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Xingyu Sun
 * @date 2019/12/26 14:20
 */
@Configuration
public class JobConfig {

    @Autowired
    private JobFactory jobFactory;
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public SchedulerFactoryBean factoryBean() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        Assert.notNull(propertiesFactoryBean.getObject());
        schedulerFactoryBean.setQuartzProperties(propertiesFactoryBean.getObject());
        schedulerFactoryBean.setDataSource(this.dataSource);
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setOverwriteExistingJobs(false);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setStartupDelay(1);
        schedulerFactoryBean.setAutoStartup(true);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(SchedulerFactoryBean factoryBean){
        return factoryBean.getScheduler();
    }
}
