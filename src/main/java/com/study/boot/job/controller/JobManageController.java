package com.study.boot.job.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
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

import javax.validation.constraints.NotBlank;
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


    private JobDao jobDao;
    private FrameJobService frameJobService;
    private FrameTriggerService frameTriggerService;

    @Autowired
    public JobManageController(JobDao jobDao, FrameJobService frameJobService, FrameTriggerService frameTriggerService) {
        this.jobDao = jobDao;
        this.frameJobService = frameJobService;
        this.frameTriggerService = frameTriggerService;
    }

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
            frameTriggerService.addFrameTrigger(model);
            return CommonResult.success();
        }
    }

    @GetMapping(value = "/trigger/detail")
    public CommonResult<Object> add(@RequestParam("id") String id) {
        if (StrUtil.isBlank(id)) {
            return CommonResult.fail("id can not be null");
        } else {
            Long jobId = Long.parseLong(id);
            FrameTriggerModel model = frameTriggerService.getTriggerByJobId(jobId);
            if (model != null) {
                return CommonResult.success(model);
            } else {
                return CommonResult.success();
            }
        }
    }

    @PostMapping(value = "/start", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> start(@NotBlank String id) {
        log.info("id is {}", id);
        boolean success = frameJobService.startJob(Long.parseLong(id));
        if (success) {
            return CommonResult.success();
        }
        return CommonResult.fail();
    }

    @PostMapping(value = "/stop", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> stop(@NotBlank String id) {
        log.info("id is {}", id);
        boolean success = frameJobService.stopJob(Long.parseLong(id));
        if (success) {
            return CommonResult.success();
        }
        return CommonResult.fail();
    }

    @PostMapping(value = "/pause", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> pause(@NotBlank String id) {
        log.info("id is {}", id);
        boolean success = frameJobService.pauseJob(Long.parseLong(id));
        if (success) {
            return CommonResult.success();
        }
        return CommonResult.fail();
    }

    @PostMapping(value = "/resume", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public CommonResult<Object> resume(@NotBlank String id) {
        log.info("id is {}", id);
        boolean success = frameJobService.resumeJob(Long.parseLong(id));
        if (success) {
            return CommonResult.success();
        }
        return CommonResult.fail();
    }

}
