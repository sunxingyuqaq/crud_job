package com.study.boot.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.study.boot.job.entity.FrameTrigger;

/**
 * @author Xingyu Sun
 * @date 2020/1/6 17:02
 */
public interface FrameTriggerService extends IService<FrameTrigger> {
    /**
     * exist
     *
     * @param triggerName
     * @param triggerGroup
     * @return
     */
    boolean exist(String triggerName, String triggerGroup);
}
