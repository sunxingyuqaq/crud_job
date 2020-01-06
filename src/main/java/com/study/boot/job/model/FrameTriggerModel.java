package com.study.boot.job.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author Xingyu Sun
 * @date 2020/1/6 10:24
 */
@Data
@Builder
public class FrameTriggerModel implements Serializable {

    @NotBlank
    private Integer jobId;
    private Integer id;
    @NotBlank
    private String triggerName;
    @NotBlank
    private String triggerGroup;
    private String cron;
    private String description;
}
