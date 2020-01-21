package com.study.boot.job.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.boot.job.dao.FrameTriggerDao;
import com.study.boot.job.entity.FrameJob;
import com.study.boot.job.entity.FrameTrigger;
import com.study.boot.job.model.FrameTriggerModel;
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
 * @date 2020/1/6 17:03
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class FrameTriggerServiceImpl extends ServiceImpl<FrameTriggerDao, FrameTrigger> implements FrameTriggerService {

    @Autowired
    private FrameJobService frameJobService;
    @Autowired
    private Scheduler scheduler;

    @Override
    public boolean exist(String triggerName, String triggerGroup) {
        FrameTrigger frameTrigger = getBaseMapper()
                .selectOne(new QueryWrapper<FrameTrigger>()
                        .eq("trigger_name", triggerName)
                        .eq("trigger_group", triggerGroup)
                );
        return frameTrigger != null;
    }

    @Override
    public FrameTriggerModel getTriggerByJobId(Long jobId) {
        FrameTrigger frameTrigger = getBaseMapper()
                .selectOne(new QueryWrapper<FrameTrigger>()
                        .eq("job_id", jobId)
                );
        FrameTriggerModel model = null;
        if (frameTrigger != null) {
            model = new FrameTriggerModel();
            BeanUtil.copyProperties(frameTrigger, model);
        }
        return model;
    }

    @Override
    public void addFrameTrigger(FrameTriggerModel model) {
        String jobId = model.getJobId();
        Long id = Long.parseLong(jobId);
        FrameJob job = frameJobService.getBaseMapper().selectById(id);
        if (job == null) {
            throw new BizException("job not find in database", ResultEnums.BIZ_ERROR);
        }
        FrameTrigger frameTrigger;
        try {
            frameTrigger = FrameTrigger
                    .builder()
                    .jobId(id).triggerName(model.getTriggerName()).triggerGroup(model.getTriggerGroup())
                    .scheduleName(scheduler.getSchedulerName())
                    .status("-1")
                    .cron(model.getCron())
                    .description(model.getDescription())
                    .operateDate(new Date())
                    .updateDate(new Date())
                    .build();
            save(frameTrigger);
        } catch (SchedulerException e) {
            throw new BizException("get schedule name error", ResultEnums.QUARTZ_ERROR);
        }
        job.setStatus("0");
        frameJobService.getBaseMapper().updateById(job);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(model.getTriggerName(), model.getTriggerGroup())
                .withDescription(model.getDescription())
                .withSchedule(CronScheduleBuilder.cronSchedule(model.getCron())
                        .withMisfireHandlingInstructionDoNothing())
                .build();
        try {
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            throw new BizException("scheduler addFrameTrigger error", ResultEnums.QUARTZ_ERROR);
        }
    }
}
