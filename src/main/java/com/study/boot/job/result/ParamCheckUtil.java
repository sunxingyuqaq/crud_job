package com.study.boot.job.result;

import cn.hutool.core.util.StrUtil;
import com.study.boot.job.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author Xingyu Sun
 * @date 2020/1/21 17:19
 */
@Slf4j
public class ParamCheckUtil {
    public static void checkParams(Object requestParams, String... keys) {
        if (null == requestParams) {
            throw new BizException(ResultEnums.BIZ_ERROR);
        }
        StringBuilder sb = new StringBuilder();
        for (String fieldName : keys) {
            Object value = null;
            Type type = null;
            try {
                String firstLetter = fieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + fieldName.substring(1);
                Method method = requestParams.getClass().getMethod(getter);
                value = method.invoke(requestParams);
                type = method.getReturnType();
            } catch (Exception e) {
                log.error("获取属性值出错，requestParams={}, fieldName={}", requestParams, fieldName);
            } finally {
                // 判空标志 String/Collection/Map特殊处理
                boolean isEmpty =
                        (String.class == type && StrUtil.isBlank((String) value))
                                || (Collection.class == type && CollectionUtils.isEmpty((Collection<? extends Object>) value))
                                || (Map.class == type && CollectionUtils.isEmpty((Collection<? extends Object>) value))
                                || (null == value);
                if (isEmpty) {
                    if (sb.length() != 0) {
                        sb.append(",");
                    }
                    sb.append(fieldName);
                }
            }
        }
        if (sb.length() > 0) {
            log.error(sb.toString() + ResultEnums.PARAMS_CANNOT_BE_NULL_ERROR.getMsg());
            throw new BizException(ResultEnums.PARAMS_CANNOT_BE_NULL_ERROR);
        }
    }

    public static void main(String[] args) {
        UserModel build = UserModel.builder().name("11").build();
        ParamCheckUtil.checkParams(build, "name");
    }
}

