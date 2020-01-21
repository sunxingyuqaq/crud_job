package com.study.boot.job.test;

import lombok.Data;

import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2020/1/7 17:04
 */
@Data
public class Archive {

    private String unid;
    private String mcode;
    private String type;
    private String fplace;
    private String tplace;
    private String sqtype;
    private String fktype;
    private List<String> depts;
    private String time;
    private SqData sqdata;
    private List<FkData> fkdata;
}
