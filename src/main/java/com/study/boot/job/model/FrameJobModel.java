package com.study.boot.job.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Xingyu Sun
 * @date 2020/1/2 14:19
 */
@Data
@Builder
public class FrameJobModel implements Serializable {

    private String id;
    @NotBlank
    private String jobClassName;
    @NotBlank
    private String jobName;
    @NotBlank
    private String jobGroup;
    private String description;
}
