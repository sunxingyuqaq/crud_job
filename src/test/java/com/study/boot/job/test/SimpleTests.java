package com.study.boot.job.test;

import cn.hutool.json.JSONUtil;
import com.study.boot.job.model.UserModel;
import com.study.boot.job.thread.ThreadTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Xingyu Sun
 * @date 2019/12/30 17:04
 */
@Slf4j
public class SimpleTests {

    private List<UserModel> userModels;

    @BeforeEach
    public void before() {
        userModels = new ThreadTest().getUserModels();
    }

    @Test
    public void test() throws Exception {
        List<UserModel> userModels = CompletableFuture.supplyAsync(
                () -> this.userModels.stream().sorted(Comparator.comparingInt(UserModel::getAge))
                        .filter(x -> x.getAge() >= 30)
                        .collect(Collectors.toList())
        ).thenCombine(CompletableFuture.supplyAsync(() ->
                this.userModels.stream().sorted(Comparator.comparingInt(UserModel::getAge))
                        .filter(y -> y.getAge() < 30)
                        .collect(Collectors.toList()
                        )), (x, y) -> {
            x.addAll(y);
            return x;
        }).exceptionally((args) -> new ArrayList<>()).get();
        userModels.forEach(x -> log.info(JSONUtil.toJsonStr(x)));
    }
}
