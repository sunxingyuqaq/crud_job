package com.study.boot.job.service.impl;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.boot.job.dao.FrameJobDao;
import com.study.boot.job.entity.FrameJob;
import com.study.boot.job.entity.FrameTrigger;
import com.study.boot.job.model.FrameJobModel;
import com.study.boot.job.model.FrameTriggerModel;
import com.study.boot.job.result.BizException;
import com.study.boot.job.result.ResultEnums;
import com.study.boot.job.service.FrameJobService;
import com.study.boot.job.service.FrameTriggerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 14:22
 */
@Service
public class FrameJobServiceImpl extends ServiceImpl<FrameJobDao, FrameJob> implements FrameJobService {

    @Autowired
    private Scheduler scheduler;
    @Autowired
    private FrameTriggerService frameTriggerService;

    @Override
    public boolean exist(String jobName, String jobGroup) {
        FrameJobDao baseMapper = getBaseMapper();
        QueryWrapper<FrameJob> objectQueryWrapper = new QueryWrapper<>();
        FrameJob frameJob = baseMapper.selectOne(objectQueryWrapper.eq("job_name", jobName).eq("job_group", jobGroup));
        return frameJob != null;
    }

    @Override
    public void addJob(FrameJobModel model) {
        FrameJob job;
        try {
            job = FrameJob.builder()
                    .jobClassName(model.getJobClassName())
                    .jobName(model.getJobName())
                    .jobGroup(model.getJobGroup())
                    .description(model.getDescription())
                    .scheduleName(scheduler.getSchedulerName())
                    .status("0")
                    .operateDate(new Date())
                    .updateDate(new Date())
                    .build();
            save(job);
        } catch (SchedulerException e) {
            log.error("get schedule name error", e);
            throw new BizException("get schedule name error", ResultEnums.BIZ_ERROR);
        }
    }

    @Override
    public void addJobToSchedule(FrameTriggerModel model) {
        FrameTrigger frameTrigger;
        try {
            frameTrigger = FrameTrigger
                    .builder()
                    .jobId(model.getJobId()).triggerName(model.getTriggerName()).triggerGroup(model.getTriggerGroup())
                    .scheduleName(scheduler.getSchedulerName())
                    .status("1")
                    .operateDate(new Date())
                    .updateDate(new Date())
                    .build();
            frameTriggerService.save(frameTrigger);
        } catch (SchedulerException e) {
            throw new BizException("get schedule name error", ResultEnums.BIZ_ERROR);
        }
        Long jobId = model.getJobId();
        FrameJob job = getBaseMapper().selectById(jobId);
        if (job == null) {
            throw new BizException("job not find in database", ResultEnums.BIZ_ERROR);
        }
        JobDetail jobDetail = JobBuilder.newJob(ClassUtil.loadClass(job.getJobClassName()))
                .withIdentity(job.getJobName(), job.getJobGroup())
                .withDescription(job.getDescription()).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(model.getTriggerName(), model.getTriggerGroup())
                .withSchedule(CronScheduleBuilder.cronSchedule(model.getCron()).withMisfireHandlingInstructionIgnoreMisfires())
                .build();
        job.setStatus("1");
        getBaseMapper().updateById(job);
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("add job to schedule error", e);
            throw new BizException("add job to schedule error", ResultEnums.BIZ_ERROR);
        }
    }
}
