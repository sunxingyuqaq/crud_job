package com.study.boot.job.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.study.boot.job.dao.JobDao;
import com.study.boot.job.model.FrameJobModel;
import com.study.boot.job.model.FrameTriggerModel;
import com.study.boot.job.result.CommonResult;
import com.study.boot.job.service.FrameJobService;
import com.study.boot.job.service.FrameTriggerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ClassUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2019/12/31 14:09
 */
@Slf4j
@RestController
@RequestMapping("frame/job")
public class JobManageController {

    @Autowired
    private JobDao jobDao;
    @Autowired
    private FrameJobService frameJobService;
    @Autowired
    private FrameTriggerService frameTriggerService;

    @GetMapping("/all")
    public CommonResult<Object> all(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        IPage<FrameJobModel> pages = new Page<>(page, limit);
        IPage<FrameJobModel> all = jobDao.findAll(pages);
        return CommonResult.success(all.getRecords(), all.getTotal());
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> add(@Validated FrameJobModel model) {
        boolean exist = frameJobService.exist(model.getJobName(), model.getJobGroup());
        if (exist) {
            return CommonResult.fail("jobName和jobGroup不能重复！");
        } else {
            boolean present = ClassUtils.isPresent(model.getJobClassName(), Thread.currentThread().getContextClassLoader());
            if (present) {
                frameJobService.addJob(model);
                return CommonResult.success();
            } else {
                return CommonResult.fail(model.getJobClassName() + " class not found");
            }
        }
    }

    @DeleteMapping(value = "/delete", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> delete(@RequestParam("params") List<String> params) {
        if (CollectionUtil.isEmpty(params)) {
            return CommonResult.fail("error...params can not be empty");
        } else {
            List<Long> longs = new ArrayList<>();
            for (String param : params) {
                longs.add(Long.parseLong(param));
            }
            frameJobService.removeByIds(longs);
            return CommonResult.success();
        }
    }

    @PostMapping(value = "/trigger/add", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> add(@Validated FrameTriggerModel model) {
        boolean exist = frameTriggerService.exist(model.getTriggerName(), model.getTriggerGroup());
        if (exist) {
            return CommonResult.fail("jobName和jobGroup不能重复！");
        } else {
            frameJobService.addJobToSchedule(model);
            return CommonResult.success();
        }
    }
}
