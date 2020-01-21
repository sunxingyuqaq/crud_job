package com.study.boot.job.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.boot.job.entity.FrameJob;
import com.study.boot.job.model.FrameDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 14:20
 */
@Mapper
@Repository
public interface FrameJobDao extends BaseMapper<FrameJob> {

    /**
     * getDetail
     *
     * @param id
     * @return
     */
    @Select("select * from view_job where id = #{id}")
    FrameDetail getDetail(@Param("id") long id);
}
