package com.study.boot.job.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Xingyu Sun
 * @date 2020/1/6 10:24
 */
@Data
@NoArgsConstructor
public class FrameTriggerModel implements Serializable {

    @NotBlank
    @JsonSerialize(using = ToStringSerializer.class)
    private Long jobId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @NotBlank
    private String triggerName;
    @NotBlank
    private String triggerGroup;
    private String cron;
    private String description;

    @Builder
    public FrameTriggerModel(Long jobId, Long id, String triggerName, String triggerGroup, String cron, String description) {
        this.jobId = jobId;
        this.id = id;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.cron = cron;
        this.description = description;
    }
}
