package com.study.boot.job.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/2 14:19
 */
@Data
@TableName("frame_job")
public class FrameJob extends BaseEntity {

    private static final long serialVersionUID = 3394071070563091105L;
    @TableId(type = IdType.ID_WORKER)
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
     * 执行状态  -1未配置trigger  0未执行  1执行中  2 停止   3暂停
     */
    private String status;
    private String scheduleName;

    @Builder
    public FrameJob(Long id, String jobClassName, String jobName, String jobGroup, Integer type, String description, String status, String scheduleName, Date operateDate, Date updateDate) {
        super(operateDate, updateDate);
        this.id = id;
        this.jobClassName = jobClassName;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.type = type;
        this.description = description;
        this.status = status;
        this.scheduleName = scheduleName;
    }
}
