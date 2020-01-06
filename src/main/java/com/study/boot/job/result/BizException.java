package com.study.boot.job.result;

/**
 * @author Xingyu Sun
 * @date 2020/1/3 9:22
 */
public class BizException extends RuntimeException {

    private Result result;

    public BizException(Result result) {
        super(result.getMsg());
        this.result = result;
    }

    public BizException(String message, Result result) {
        super(message);
        this.result = result;
    }

    public BizException(String message, Throwable cause, Result result) {
        super(message, cause);
        this.result = result;
    }

    public BizException(Throwable cause, Result result) {
        super(cause);
        this.result = result;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Result result) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.result = result;
    }

    public Result getResult() {
        return result;
    }
}
