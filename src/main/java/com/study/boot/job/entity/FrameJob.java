package com.study.boot.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author Xingyu Sun
 * @date 2020/1/2 14:19
 */
@Data
@Builder
@TableName("frame_job")
public class FrameJob extends BaseEntity {

    private static final long serialVersionUID = 3394071070563091105L;
    @TableId(type = IdType.ID_WORKER)
    private Integer id;
    private String jobClassName;
    private String jobName;
    private String jobGroup;
    /**
     * 执行类型  0自动  1手动  默认1手动
     */
    private Integer type;
    private String description;
    private String status;
    private String scheduleName;
}
