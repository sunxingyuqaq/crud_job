package com.study.boot.job.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.study.boot.job.model.FrameJobModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author Xingyu Sun
 * @date 2020/1/2 9:07
 */
@Mapper
@Repository
public interface JobDao {

    /**
     * find all jobs
     * @return l
     * @param pages p
     */
    @Select("SELECT * FROM QRTZ_JOB_DETAILS")
    @Results({
            @Result(column = "job_class_name", property = "jobClassName", javaType = String.class),
            @Result(column = "job_name", property = "jobName", javaType = String.class),
            @Result(column = "job_group", property = "jobGroup", javaType = String.class),
            @Result(column = "description", property = "description", javaType = String.class)
    })
    IPage<FrameJobModel> findAll(IPage<FrameJobModel> pages);
}
