package com.study.boot.job.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Xingyu Sun
 * @date 2019/12/30 15:47
 */
@Data
@Builder
public class UserModel implements Serializable {

    private String name;
    private String id;
    private Integer age;
}
