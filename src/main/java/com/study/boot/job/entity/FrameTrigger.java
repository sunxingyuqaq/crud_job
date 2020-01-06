package com.study.boot.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 10:38
 */
@Data
@Builder
@TableName("frame_trigger")
public class FrameTrigger extends BaseEntity {

    private static final long serialVersionUID = -5751770961688851358L;

    @TableId(type = IdType.ID_WORKER)
    private Integer id;
    private Integer jobId;
    private String triggerName;
    private String triggerGroup;
    private String status;
    private String scheduleName;
}
