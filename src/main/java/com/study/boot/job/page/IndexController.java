package com.study.boot.job.page;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2019/12/30 14:55
 */
@Slf4j
@Controller
public class IndexController {

    @GetMapping({"/index", "/"})
    public String index(Model model) {
        model.addAttribute("name", "test");
        List<String> in = new ArrayList<>();
        in.add("test0");
        in.add("test1");
        in.add("test2");
        in.add("test3");
        model.addAttribute("list", in);
        model.addAttribute("flag", true);
        model.addAttribute("today", new Date());
        return "index";
    }

    @GetMapping("/jobs")
    public String jobs() {
        return "jobs";
    }

    @GetMapping("/job/job_add")
    public String jobAdd() {
        return "job/job_add";
    }

    @GetMapping("/job/job_trigger_add")
    public String jobTriggerAdd() {
        return "job/job_trigger_add";
    }

}
