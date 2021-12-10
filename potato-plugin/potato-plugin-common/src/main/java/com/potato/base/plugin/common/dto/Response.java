package com.potato.base.plugin.common.dto;

/**
 * 返回
 *
 * @author lizhifu
 * @date 2021/12/9
 */
public class Response extends DTO{
    private static final long serialVersionUID = 1L;
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 错误码
     */
    private String errCode;
    /**
     * 错误信息
     */
    private String errMessage;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", errCode='" + errCode + '\'' +
                ", errMessage='" + errMessage + '\'' +
                '}';
    }

    public static Response buildSuccess() {
        Response response = new Response();
        response.setSuccess(true);
        return response;
    }

    public static Response buildFailure(String errCode, String errMessage) {
        Response response = new Response();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }
}
