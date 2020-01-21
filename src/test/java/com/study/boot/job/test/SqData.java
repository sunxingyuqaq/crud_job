package com.study.boot.job.test;

import lombok.Data;

import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2020/1/7 17:11
 */
@Data
public class SqData {
    private String title;
    private String deptname;
    private String time;
    private List<Opinion> yj;
    private People people;
    private List<FileData> sqcl;
}
