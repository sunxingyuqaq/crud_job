package com.study.boot.job;

import cn.hutool.core.lang.Assert;
import com.study.boot.job.dao.FrameJobDao;
import com.study.boot.job.model.FrameDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JobApplicationTests {

    @Autowired
    private FrameJobDao frameJobDao;

    @Test
    void contextLoads() {
        FrameDetail detail = frameJobDao.getDetail(1111111111111111111L);
        Assert.notNull(detail, "detail not be null");
    }

}
