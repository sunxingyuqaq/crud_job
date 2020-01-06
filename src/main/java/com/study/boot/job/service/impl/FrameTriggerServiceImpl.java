package com.study.boot.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.boot.job.dao.FrameTriggerDao;
import com.study.boot.job.entity.FrameTrigger;
import com.study.boot.job.service.FrameTriggerService;
import org.springframework.stereotype.Service;

/**
 * @author Xingyu Sun
 * @date 2020/1/6 17:03
 */
@Service
public class FrameTriggerServiceImpl extends ServiceImpl<FrameTriggerDao, FrameTrigger> implements FrameTriggerService {
    @Override
    public boolean exist(String triggerName, String triggerGroup) {
        FrameTrigger frameTrigger = getBaseMapper()
                .selectOne(new QueryWrapper<FrameTrigger>()
                        .eq("trigger_name", triggerName)
                        .eq("trigger_group", triggerGroup)
                );
        return frameTrigger != null;
    }
}
