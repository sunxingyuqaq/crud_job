package com.study.boot.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 10:38
 */
@Data
@TableName("frame_trigger")
public class FrameTrigger extends BaseEntity {

    private static final long serialVersionUID = -5751770961688851358L;

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private Long jobId;
    private String triggerName;
    private String triggerGroup;
    private String status;
    private String cron;
    private String description;
    private String scheduleName;

    @Builder
    public FrameTrigger(Long id, Long jobId, String triggerName, String triggerGroup, String status, String cron, String description, String scheduleName, Date operateDate, Date updateDate) {
        super(operateDate, updateDate);
        this.id = id;
        this.jobId = jobId;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.status = status;
        this.cron = cron;
        this.description = description;
        this.scheduleName = scheduleName;
    }
}
