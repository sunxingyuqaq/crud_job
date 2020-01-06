package com.study.boot.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.boot.job.entity.FrameTrigger;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 14:20
 */
@Mapper
@Repository
public interface FrameTriggerDao extends BaseMapper<FrameTrigger> {
}
