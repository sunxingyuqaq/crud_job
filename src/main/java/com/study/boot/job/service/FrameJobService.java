package com.study.boot.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.boot.job.entity.FrameJob;
import com.study.boot.job.model.FrameDetail;
import com.study.boot.job.model.FrameJobModel;

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
     * startJob
     *
     * @param id
     * @return
     */
    boolean startJob(long id);

    /**
     * stopJob
     *
     * @param id
     * @return
     */
    boolean stopJob(long id);

    /**
     * pauseJob
     *
     * @param id
     * @return
     */
    boolean pauseJob(long id);

    /**
     * resumeJob
     *
     * @param id
     * @return
     */
    boolean resumeJob(long id);

    /**
     * updateStatus
     *
     * @param status
     * @param detail
     */
    void updateStatus(String status, FrameDetail detail);
}
