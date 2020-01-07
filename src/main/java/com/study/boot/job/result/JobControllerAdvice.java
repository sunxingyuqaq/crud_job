package com.study.boot.job.result;

import cn.hutool.core.lang.Dict;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 9:25
 */
@Slf4j
@RestControllerAdvice
public class JobControllerAdvice {

    @ExceptionHandler(BizException.class)
    public CommonResult<Object> handle(BizException e) {
        Result result = e.getResult();
        String message = e.getMessage();
        return CommonResult.fail(message, result.getCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public CommonResult<ResultEnums> handle(RuntimeException e) {
        log.error("error ha", e);
        return CommonResult.fail(ResultEnums.SYSTEM_ERROR, e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<Object> handle(BindException e) {
        List<Dict> dictList = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            String field = fieldError.getField();
            Object rejectedValue = fieldError.getRejectedValue();
            String defaultMessage = fieldError.getDefaultMessage();
            Dict dict = Dict.create();
            dict.set("field", field);
            dict.set("value", rejectedValue);
            dict.set("message", defaultMessage);
            dictList.add(dict);
        }
        return CommonResult.fail(dictList);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResult<Object> missingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("param error...", e);
        String message = e.getMessage();
        return CommonResult.fail(message);
    }
}
