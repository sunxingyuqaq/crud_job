package com.study.boot.job.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author Xingyu Sun
 * @date 2019/12/27 10:14
 */
@Slf4j
@Service
public class UserService {

    public void say() {
        log.info("当前时间是：【{}】", LocalDateTime.now());
    }
}
