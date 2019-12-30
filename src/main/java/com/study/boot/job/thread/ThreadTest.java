package com.study.boot.job.thread;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RandomUtil;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.study.boot.job.model.UserModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author Xingyu Sun
 * @date 2019/12/30 15:46
 */
@Slf4j
public class ThreadTest {

    private List<UserModel> userModels;

    {
        userModels = new ArrayList<>();
        userModels.add(UserModel.builder().age(11).id("1").name("tom").build());
        userModels.add(UserModel.builder().age(13).id("2").name("jerry").build());
        userModels.add(UserModel.builder().age(21).id("3").name("sorry").build());
        userModels.add(UserModel.builder().age(31).id("4").name("money").build());
        userModels.add(UserModel.builder().age(41).id("5").name("test").build());
        userModels.add(UserModel.builder().age(15).id("6").name("development").build());
        userModels.add(UserModel.builder().age(61).id("7").name("statistic").build());
        userModels.add(UserModel.builder().age(17).id("8").name("ketty").build());
        userModels.add(UserModel.builder().age(81).id("9").name("hello").build());
    }

    public static void main(String[] args) throws Exception {
        ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
        ExecutorService executors = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MINUTES,
                new LinkedBlockingDeque<>(1024),
                builder.setNameFormat("test-pool-%d").build());
        ThreadTest threadTest = new ThreadTest();
        long start = System.currentTimeMillis();
        List<Future<String>> results = new ArrayList<>();
        for (UserModel model : threadTest.userModels) {
            Future<String> submit = executors.submit(() -> {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 4));
                return model.getId() + "---" + model.getName();
            });
            results.add(submit);
        }
        results.forEach(x -> {
            try {
                Console.log(x.get(2, TimeUnit.SECONDS));
            } catch (Exception e) {
                log.error("time out error", e);
                Console.log("null");
            }
        });
        long end = System.currentTimeMillis();
        //18150-6696
        Console.log(end - start);
        executors.shutdown();
    }
}
