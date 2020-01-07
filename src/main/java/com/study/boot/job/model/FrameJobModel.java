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
 * @date 2020/1/2 14:19
 */
@Data
@NoArgsConstructor
public class FrameJobModel implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @NotBlank
    private String jobClassName;
    @NotBlank
    private String jobName;
    @NotBlank
    private String jobGroup;
    private String description;

    @Builder
    public FrameJobModel(Long id, String jobClassName, String jobName, String jobGroup, String description) {
        this.id = id;
        this.jobClassName = jobClassName;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.description = description;
    }
}
