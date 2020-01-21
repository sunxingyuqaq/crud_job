package com.study.boot.job.service.impl;

import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.boot.job.dao.FrameJobDao;
import com.study.boot.job.entity.FrameJob;
import com.study.boot.job.entity.FrameTrigger;
import com.study.boot.job.model.FrameDetail;
import com.study.boot.job.model.FrameJobModel;
import com.study.boot.job.result.BizException;
import com.study.boot.job.result.ResultEnums;
import com.study.boot.job.service.FrameJobService;
import com.study.boot.job.service.FrameTriggerService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 14:22
 */
@Service
@Transactional(rollbackFor = Exception.class)
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
                    .type(1)
                    .status("-1")
                    .operateDate(new Date())
                    .updateDate(new Date())
                    .build();
            save(job);
        } catch (SchedulerException e) {
            log.error("get schedule name error", e);
            throw new BizException("get schedule name error", ResultEnums.QUARTZ_ERROR);
        }
        JobDetail jobDetail = JobBuilder.newJob(ClassUtil.loadClass(model.getJobClassName()))
                .withIdentity(model.getJobName(), model.getJobGroup())
                .withDescription(model.getDescription()).build();
        try {
            scheduler.addJob(jobDetail, false);
        } catch (SchedulerException e) {
            log.error("schedule addJob error", e);
            throw new BizException("schedule addJob error", ResultEnums.QUARTZ_ERROR);
        }
    }

    @Override
    public boolean startJob(long id) {
        FrameDetail detail = getBaseMapper().getDetail(id);
        JobDetail jobDetail = JobBuilder.newJob(ClassUtil.loadClass(detail.getJobClassName()))
                .withIdentity(detail.getJobName(), detail.getJobGroup())
                .withDescription(detail.getDescription()).build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(detail.getTriggerName(), detail.getTriggerGroup())
                .withDescription(detail.getTriggerDesc())
                .withSchedule(CronScheduleBuilder.cronSchedule(detail.getCron()).withMisfireHandlingInstructionDoNothing())
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("schedule startJob error", e);
            throw new BizException("startJob error", ResultEnums.QUARTZ_ERROR);
        }
        updateStatus("1", detail);
        return true;
    }

    @Override
    public boolean stopJob(long id) {
        FrameDetail detail = getBaseMapper().getDetail(id);
        TriggerKey key = TriggerKey.triggerKey(detail.getTriggerName(), detail.getTriggerGroup());
        try {
            scheduler.unscheduleJob(key);
        } catch (SchedulerException e) {
            log.error("schedule stopJob error", e);
            throw new BizException("stopJob error", ResultEnums.QUARTZ_ERROR);
        }
        updateStatus("2", detail);
        return true;
    }

    @Override
    public boolean pauseJob(long id) {
        FrameDetail detail = getBaseMapper().getDetail(id);
        JobKey jobKey = JobKey.jobKey(detail.getJobName(), detail.getJobGroup());
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            log.error("schedule pauseJob error", e);
            throw new BizException("pauseJob error", ResultEnums.QUARTZ_ERROR);
        }
        updateStatus("3", detail);
        return true;
    }

    @Override
    public boolean resumeJob(long id) {
        FrameDetail detail = getBaseMapper().getDetail(id);
        JobKey jobKey = JobKey.jobKey(detail.getJobName(), detail.getJobGroup());
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            log.error("schedule resumeJob error", e);
            throw new BizException("resumeJob error", ResultEnums.QUARTZ_ERROR);
        }
        updateStatus("1", detail);
        return true;
    }

    @Override
    public void updateStatus(String status, FrameDetail detail) {
        TriggerKey key = TriggerKey.triggerKey(detail.getTriggerName(), detail.getTriggerGroup());
        try {
            Trigger.TriggerState triggerState = scheduler.getTriggerState(key);
            String name = triggerState.name();
            FrameTrigger frameTrigger = frameTriggerService.getBaseMapper().selectById(detail.getTriggerId());
            frameTrigger.setStatus(name);
            frameTrigger.setUpdateDate(new Date());
            frameTriggerService.getBaseMapper().updateById(frameTrigger);
        } catch (SchedulerException e) {
            log.error("get trigger state error", e);
        }
        FrameJob frameJob = getBaseMapper().selectById(detail.getId());
        frameJob.setStatus(status);
        frameJob.setUpdateDate(new Date());
        updateById(frameJob);
    }
}
