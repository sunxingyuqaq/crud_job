package com.study.boot.job.test;

import cn.hutool.core.date.StopWatch;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
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

    @Test
    public void pares() {
        String json = "{\"unid\":\"4DAF7FF356525FB94825849000482B25\",\"mcode\":\"jxssjyj-0014\",\"type\":\"1\",\"fplace\":\"南湖区\",\"tplace\":\"市本级\",\"sqtype\":\"1\",\"fktype\":\"2\",\"depts\":[\"钉钉单位唯一编码1\",\"钉钉单位唯一编码2\",\"钉钉单位唯一编码3\"],\"time\":\"2019-12-21 14:38:27\",\"sqdata\":{\"title\":\"XXX学校申请校车使用许可审核登记“一件事”\",\"deptname\":\"钉钉单位唯一编码4\",\"time\":\"2019-12-22 9:28:56\",\"yj\":[{\"name\":\"张三\",\"time\":\"2019-12-22 9:20:36\",\"content\":\"材料齐全\"},{\"name\":\"张三\",\"time\":\"2019-12-22 9:22:28\",\"content\":\"请审批\"}],\"people\":{\"name\":\"张三\",\"phone\":\"13000000000\"},\"sqcl\":[{\"name\":\"申请材料1.doc\",\"data\":\"二进制流数据\"},{\"name\":\"申请材料2.pdf\",\"data\":\"二进制流数据\"},{\"name\":\"申请材料3.doc\",\"data\":\"二进制流数据\"}]},\"fkdata\":[{\"deptname\":\"钉钉单位唯一编码5\",\"time\":\"2019-12-22 10:28:56\",\"yj\":[{\"name\":\"李四\",\"time\":\"2019-12-22 10:20:36\",\"content\":\"已受理\"},{\"name\":\"李四\",\"time\":\"2019-12-22 10:22:18\",\"content\":\"审批完成\"}],\"people\":{\"name\":\"李四\",\"phone\":\"13000000001\"},\"fkcl\":[{\"name\":\"市运管局反馈材料1.doc\",\"data\":\"二进制流数据\"},{\"name\":\"市运管局反馈材料2.doc\",\"data\":\"二进制流数据\"},{\"name\":\"市运管局反馈材料3.pdf\",\"data\":\"二进制流数据\"}]},{\"deptname\":\"钉钉单位唯一编码6\",\"time\":\"2019-12-22 12:28:56\",\"yj\":[{\"name\":\"王五\",\"time\":\"2019-12-22 11:45:36\",\"content\":\"待审核\"},{\"name\":\"王五\",\"time\":\"2019-12-22 11:56:28\",\"content\":\"已审批\"}],\"people\":{\"name\":\"王五\",\"phone\":\"13000000002\"},\"fkcl\":[{\"name\":\"市公安交警支队反馈材料1.doc\",\"data\":\"二进制流数据\"}]},{\"deptname\":\"钉钉单位唯一编码7\",\"time\":\"2019-12-22 14:28:56\",\"yj\":[{\"name\":\"赵六\",\"time\":\"2019-12-22 14:11:36\",\"content\":\"材料不全，请补充\"},{\"name\":\"赵六\",\"time\":\"2019-12-22 14:19:21\",\"content\":\"审批通过\"}],\"people\":{\"name\":\"赵六\",\"phone\":\"13000000006\"},\"fkcl\":[{\"name\":\"区人民政府反馈材料1.pdf\",\"data\":\"二进制流数据\"},{\"name\":\"区人民政府反馈材料2.doc\",\"data\":\"二进制流数据\"},{\"name\":\"区人民政府反馈材料3.doc\",\"data\":\"二进制流数据\"}]}]}";
        StopWatch stopWatch = new StopWatch("test");
        stopWatch.start("task-1");
        Archive archive = JSONUtil.toBean(json, Archive.class);
        stopWatch.stop();
        stopWatch.start("task-2");
        Archive object = JSON.parseObject(json, Archive.class);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        System.out.println(archive);
        System.out.println(object);
    }
}
