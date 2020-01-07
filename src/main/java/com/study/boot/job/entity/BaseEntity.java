package com.study.boot.job.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/6 10:31
 */
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {

    protected Date operateDate;
    protected Date updateDate;
}
