package com.study.boot.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.boot.job.entity.FrameJob;
import com.study.boot.job.model.FrameJobModel;
import com.study.boot.job.model.FrameTriggerModel;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 14:21
 */
public interface FrameJobService extends IService<FrameJob> {

    /**
     * exist
     *
     * @param jobName
     * @param jobGroup
     * @return
     */
    boolean exist(String jobName, String jobGroup);

    /**
     * addJob
     *
     * @param model
     */
    void addJob(FrameJobModel model);

    /**
     * addJobToSchedule
     *
     * @param model
     */
    void addJobToSchedule(FrameTriggerModel model);
}
