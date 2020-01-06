package com.study.boot.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.boot.job.entity.FrameJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 14:20
 */
@Mapper
@Repository
public interface FrameJobDao extends BaseMapper<FrameJob> {
}
