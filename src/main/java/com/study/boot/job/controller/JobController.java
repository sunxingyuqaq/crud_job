package com.study.boot.job.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import com.study.boot.job.task.MyJob;
import com.study.boot.job.task.MyTask;
import com.study.boot.job.utils.JobUtils;
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
@RestController
@RequestMapping("job")
public class JobController {

    private SchedulerFactory schedulerFactory;

    public JobController(SchedulerFactory schedulerFactory) {
        this.schedulerFactory = schedulerFactory;
    }

    @GetMapping("all")
    public List<Dict> allJobs() throws Exception {
        List<Dict> dict = new ArrayList<>();
        Scheduler scheduler = schedulerFactory.getScheduler();
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
        return dict;
    }

    @GetMapping("task")
    public Dict startTask() {
        try {
            JobDetail build = JobBuilder.newJob(MyTask.class).withIdentity("my-task", "test").build();
            TriggerBuilder<Trigger> newTrigger = TriggerBuilder.newTrigger();
            newTrigger.withIdentity("trigger-2", "test");
            newTrigger.startNow();
            newTrigger.withSchedule(CronScheduleBuilder.cronSchedule("0/15 * * * * ?"));
            Trigger trigger = newTrigger.build();
            schedulerFactory.getScheduler().scheduleJob(build, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Dict.create().set("msg", "ok").set("code", 200);
    }

    @GetMapping("/stop/{name}")
    public Dict stopJob(@PathVariable String name) throws SchedulerException {
        Console.log(name);
        String jobName = "job";
        String taskName = "task";
        Scheduler scheduler = schedulerFactory.getScheduler();
        if (jobName.equals(name)) {
            TriggerKey key = TriggerKey.triggerKey("trigger-1", "test");
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
            scheduler.deleteJob(JobKey.jobKey("my-job", "test"));
        } else if (taskName.equals(name)) {
            TriggerKey key = TriggerKey.triggerKey("trigger-2", "test");
            scheduler.pauseTrigger(key);
            scheduler.unscheduleJob(key);
            scheduler.deleteJob(JobKey.jobKey("my-task", "test"));
        }
        return Dict.create().set("msg", "ok").set("code", 200);
    }

    @GetMapping("start-all")
    public Dict startAll() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Dict.create().set("msg", "ok").set("code", 200);
    }

    @GetMapping("add-all")
    public Dict addAll() {
        try {
            JobUtils.addJob("my-job", "test",
                    "trigger-1", "test",
                    MyJob.class, "0/10 * * * * ?");
            this.startTask();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Dict.create().set("msg", "ok").set("code", 200);
    }
}
