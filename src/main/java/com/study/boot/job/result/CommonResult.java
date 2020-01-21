package com.study.boot.job.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 9:13
 */
@Data
public class CommonResult<T> implements Serializable {

    private String msg;
    private String code;
    private Boolean success;
    private T data;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;
    private Date date;

    private CommonResult() {
    }

    public CommonResult(T data) {
        this.date = new Date();
        this.data = data;
    }

    public static CommonResult<Object> success() {
        CommonResult<Object> result = new CommonResult<>(null);
        result.setMsg(ResultEnums.SUCCESS.getMsg());
        result.setCode(ResultEnums.SUCCESS.getCode());
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        return base(ResultEnums.SUCCESS.getCode(), ResultEnums.SUCCESS.getMsg(), true, data);
    }

    private static <T> CommonResult<T> base(String code, String msg, boolean success, T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setMsg(msg);
        result.setCode(code);
        result.setSuccess(success);
        result.setData(data);
        result.setDate(new Date());
        return result;
    }

    public static <T> CommonResult<T> success(T data, long total) {
        CommonResult<T> result = success(data);
        result.setTotal(total);
        return result;
    }

    public static <T> CommonResult<T> fail() {
        CommonResult<T> result = new CommonResult<>(null);
        result.setMsg(ResultEnums.FAIL.getMsg());
        result.setCode(ResultEnums.FAIL.getCode());
        return result;
    }

    public static <T> CommonResult<T> fail(T data) {
        return base(ResultEnums.BINDING_ERROR.getCode(), ResultEnums.BINDING_ERROR.getMsg(), false, data);
    }

    public static <T> CommonResult<T> fail(String msg) {
        CommonResult<T> fail = fail((T) null);
        fail.setMsg(msg);
        return fail;
    }

    public static <T> CommonResult<T> fail(String msg, String code) {
        CommonResult<T> fail = fail((T) null);
        fail.setMsg(msg);
        fail.setCode(code);
        return fail;
    }

    public static <T> CommonResult<T> fail(ResultEnums resultEnums) {
        CommonResult<T> result = new CommonResult<>(null);
        result.setMsg(resultEnums.getMsg());
        result.setCode(resultEnums.getCode());
        return result;
    }

    public static <T> CommonResult<T> fail(ResultEnums resultEnums, String error) {
        CommonResult<T> result = new CommonResult<>(null);
        result.setMsg(error);
        result.setCode(resultEnums.getCode());
        return result;
    }

    public static CommonResult<Result> fail(Result result) {
        CommonResult<Result> results = new CommonResult<>(null);
        results.setCode(result.getCode());
        results.setMsg(result.getMsg());
        return results;
    }
}
