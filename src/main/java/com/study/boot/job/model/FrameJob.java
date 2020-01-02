package com.study.boot.job.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Xingyu Sun
 * @date 2020/1/2 14:19
 */
@Data
@Builder
public class FrameJob implements Serializable {

    private String jobClassName;
    private String jobName;
    private String jobGroup;
    private String description;
}
