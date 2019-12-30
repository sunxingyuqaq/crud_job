package com.study.boot.job.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import com.study.boot.job.task.MyJob;
import com.study.boot.job.task.MyTask;
import com.study.boot.job.utils.JobUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Xingyu Sun
 * @date 2019/12/26 14:18
 */
@Slf4j
@RestController
@RequestMapping("job")
public class JobController {

    private final static String JOB_NAME = "job";
    private final static String TASK_NAME = "task";

    private Scheduler scheduler;
    private JobUtils jobUtils;

    public JobController(Scheduler scheduler, JobUtils jobUtils) {
        this.scheduler = scheduler;
        this.jobUtils = jobUtils;
    }

    @GetMapping("all")
    public Dict allJobs() throws Exception {
        List<Dict> dict = new ArrayList<>();
        Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyGroup());
        jobKeys.forEach(x -> {
            String group = x.getGroup();
            String name = x.getName();
            try {
                JobDetail jobDetail = scheduler.getJobDetail(x);
                Dict dict1 = Dict.create();
                dict1.set("group", group);
                dict1.set("name", name);
                dict1.set("class", jobDetail.getJobClass());
                dict.add(dict1);
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        });
        return Dict.create().set("data", dict).set("msg", "get all jobs ok").set("code", 200);
    }

    @GetMapping("task")
    public Dict startTask() {
        try {
            JobDetail build = JobBuilder.newJob(MyTask.class).withIdentity("my-task", "test").build();
            TriggerBuilder<Trigger> newTrigger = TriggerBuilder.newTrigger();
            newTrigger.withIdentity("trigger-2", "test");
            newTrigger.startNow();
            newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"));
            Trigger trigger = newTrigger.build();
            scheduler.scheduleJob(build, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Dict.create().set("msg", "start task job ok").set("code", 200);
    }

    @GetMapping("/stop/{name}")
    public Dict stopJob(@PathVariable String name) throws SchedulerException {
        Console.log(name);
        if (JOB_NAME.equals(name)) {
            TriggerKey key = TriggerKey.triggerKey("trigger-1", "test");
            scheduler.pauseTrigger(key);
            scheduler.pauseJob(JobKey.jobKey("my-job", "test"));
        } else if (TASK_NAME.equals(name)) {
            TriggerKey key = TriggerKey.triggerKey("trigger-2", "test");
            scheduler.pauseTrigger(key);
            scheduler.pauseJob(JobKey.jobKey("my-task", "test"));
        }
        return Dict.create().set("msg", "stop ok").set("code", 200);
    }

    @GetMapping("/resume/{name}")
    public Dict resumeJob(@PathVariable String name) throws SchedulerException {
        Console.log(name);
        if (JOB_NAME.equals(name)) {
            JobKey key = JobKey.jobKey("my-job", "test");
            scheduler.resumeTrigger(TriggerKey.triggerKey("trigger-1", "test"));
            scheduler.resumeJob(key);
        } else if (TASK_NAME.equals(name)) {
            scheduler.resumeTrigger(TriggerKey.triggerKey("trigger-2", "test"));
            JobKey key = JobKey.jobKey("my-task", "test");
            scheduler.resumeJob(key);
        }
        return Dict.create().set("msg", "resume " + name + " ok").set("code", 200);
    }

    @GetMapping("/del/{name}")
    public Dict delJob(@PathVariable String name) throws SchedulerException {
        log.info("name is {} ", name);
        if (JOB_NAME.equals(name)) {
            TriggerKey key = TriggerKey.triggerKey("trigger-1", "test");
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
            scheduler.deleteJob(JobKey.jobKey("my-job", "test"));
        } else if (TASK_NAME.equals(name)) {
            TriggerKey key = TriggerKey.triggerKey("trigger-2", "test");
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
            scheduler.deleteJob(JobKey.jobKey("my-task", "test"));
        }
        return Dict.create().set("msg", "del ok").set("code", 200);
    }

    @GetMapping("start-all")
    public Dict startAll() {
        jobUtils.startJobs();
        return Dict.create().set("msg", "start all jobs ok").set("code", 200);
    }

    @GetMapping("add-all")
    public Dict addAll() {
        try {
            jobUtils.addJob("my-job", "test",
                    "trigger-1", "test",
                    MyJob.class, "0/10 * * * * ?");
            this.startTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Dict.create().set("msg", "add all jobs ok").set("code", 200);
    }
}
