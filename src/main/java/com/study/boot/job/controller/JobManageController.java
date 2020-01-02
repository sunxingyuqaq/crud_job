package com.study.boot.job.controller;

import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.boot.job.dao.JobDao;
import com.study.boot.job.model.FrameJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Xingyu Sun
 * @date 2019/12/31 14:09
 */
@Slf4j
@RestController
@RequestMapping("frame")
public class JobManageController {

    @Autowired
    private JobDao jobDao;

    @GetMapping("/jobs")
    public Dict all(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        IPage<FrameJob> pages = new Page<>(page, limit);
        IPage<FrameJob> all = jobDao.findAll(pages);
        return Dict.create()
                .set("data",all.getRecords())
                .set("total",all.getTotal())
                .set("code",200)
                .set("msg","ok");
    }
}
