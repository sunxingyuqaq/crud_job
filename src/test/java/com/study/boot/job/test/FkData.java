package com.study.boot.job.test;

import lombok.Data;

import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2020/1/7 17:13
 */
@Data
public class FkData {
    private String deptname;
    private String time;
    private List<Opinion> yj;
    private People people;
    private List<FileData> fkcl;
}
