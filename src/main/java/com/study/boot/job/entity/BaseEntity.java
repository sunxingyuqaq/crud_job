package com.study.boot.job.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/6 10:31
 */

public class BaseEntity implements Serializable {

    private Date operateDate;
    private Date updateDate;
}
