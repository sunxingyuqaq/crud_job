package com.study.boot.job.test;

import com.study.boot.job.model.UserModel;
import com.study.boot.job.thread.ThreadTest;
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
public class SimpleTests {

    private List<UserModel> userModels;

    @BeforeEach
    public void before() {
        userModels = new ThreadTest().getUserModels();
    }

    @Test
    public void test() throws Exception {
        Object o = CompletableFuture.supplyAsync(
                () -> userModels.stream().sorted(Comparator.comparingInt(UserModel::getAge))
                        .filter(x -> x.getAge() >= 30)
                        .collect(Collectors.toList())
        ).thenCombine(CompletableFuture.supplyAsync(() ->
                userModels.stream().sorted(Comparator.comparingInt(UserModel::getAge))
                        .filter(y -> y.getAge() < 30)
                        .collect(Collectors.toList()
                        )), (x, y) -> {
            x.addAll(y);
            return x;
        }).exceptionally((args) -> new ArrayList<>()).get();
        System.out.println(o);
    }
}
