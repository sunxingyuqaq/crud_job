package com.study.boot.job.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 9:13
 */
@Data
public class CommonResult<T> {

    private String msg;
    private String code;
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

    public CommonResult(String msg, String code, T data) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.date = new Date();
    }

    public CommonResult(String msg, String code, T data, Long total) {
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.total = total;
        this.date = new Date();
    }

    public static CommonResult<Object> success() {
        CommonResult<Object> result = new CommonResult<>(null);
        result.setMsg(ResultEnums.SUCCESS.getMsg());
        result.setCode(ResultEnums.SUCCESS.getCode());
        return result;
    }

    public static CommonResult<Object> success(Object data) {
        CommonResult<Object> result = new CommonResult<>(data);
        result.setMsg(ResultEnums.SUCCESS.getMsg());
        result.setCode(ResultEnums.SUCCESS.getCode());
        return result;
    }

    public static CommonResult<Object> success(Object data, long total) {
        CommonResult<Object> result = new CommonResult<>(data);
        result.setMsg(ResultEnums.SUCCESS.getMsg());
        result.setCode(ResultEnums.SUCCESS.getCode());
        result.setTotal(total);
        return result;
    }

    public static CommonResult<Object> fail() {
        CommonResult<Object> result = new CommonResult<>(null);
        result.setMsg(ResultEnums.FAIL.getMsg());
        result.setCode(ResultEnums.FAIL.getCode());
        return result;
    }

    public static CommonResult<Object> fail(Object data) {
        CommonResult<Object> result = new CommonResult<>(data);
        result.setMsg(ResultEnums.BINDING_ERROR.getMsg());
        result.setCode(ResultEnums.BINDING_ERROR.getCode());
        return result;
    }

    public static CommonResult<Object> fail(String msg) {
        CommonResult<Object> result = new CommonResult<>(null);
        result.setMsg(msg);
        result.setCode(ResultEnums.FAIL.getCode());
        return result;
    }

    public static CommonResult<Object> fail(String msg, String code) {
        CommonResult<Object> result = new CommonResult<>(null);
        result.setMsg(msg);
        result.setCode(code);
        return result;
    }

    public static CommonResult<ResultEnums> fail(ResultEnums resultEnums) {
        CommonResult<ResultEnums> result = new CommonResult<>(null);
        result.setMsg(resultEnums.getMsg());
        result.setCode(resultEnums.getCode());
        return result;
    }

    public static CommonResult<ResultEnums> fail(ResultEnums resultEnums, String error) {
        CommonResult<ResultEnums> result = new CommonResult<>(null);
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
