package com.study.boot.job.result;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 9:16
 */
public enum ResultEnums implements Result {

    /**
     * success
     */
    SUCCESS("200", "msg"),
    FAIL("4000", "fail"),
    BINDING_ERROR("6000", "bind error"),
    SYSTEM_ERROR("5000", "system error"),
    OPERATE_FORBID("4300", "forbidden"),
    NOT_FOUND("4004", "resource not found"),
    QUARTZ_ERROR("8000", "resource not found"),
    BIZ_ERROR("3000", "biz error");

    private String code;
    private String msg;

    ResultEnums(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
