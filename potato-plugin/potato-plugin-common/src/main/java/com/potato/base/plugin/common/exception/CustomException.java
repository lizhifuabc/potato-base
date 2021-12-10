package com.potato.base.plugin.common.exception;

/**
 * 自定义异常
 *
 * @author lizhifu
 * @date 2021/12/9
 */
public abstract class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private String errCode;

    public CustomException(String errMessage) {
        super(errMessage);
    }

    public CustomException(String errCode, String errMessage) {
        super(errMessage);
        this.errCode = errCode;
    }

    public CustomException(String errMessage, Throwable e) {
        super(errMessage, e);
    }

    public CustomException(String errCode, String errMessage, Throwable e) {
        super(errMessage, e);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
