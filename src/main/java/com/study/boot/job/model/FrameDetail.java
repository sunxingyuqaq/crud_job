package com.study.boot.job.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.study.boot.job.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Xingyu Sun
 * @date 2020/1/9 8:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("view_job")
public class FrameDetail extends BaseEntity {

    private Long id;
    private String jobClassName;
    private String jobName;
    private String jobGroup;
    /**
     * 执行类型  0自动  1手动  默认1手动
     */
    private Integer type;
    private String description;
    /**
     * 执行状态  0未执行  1执行中   默认0
     */
    private String status;
    private String scheduleName;

    private Long triggerId;
    private String triggerName;
    private String triggerGroup;
    private String cron;
    private String triggerDesc;
}
