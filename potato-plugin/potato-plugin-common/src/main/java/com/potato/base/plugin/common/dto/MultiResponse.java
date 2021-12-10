package com.potato.base.plugin.common.dto;

import java.util.Collections;
import java.util.List;

/**
 * 多结果返回
 *
 * @author lizhifu
 * @date 2021/12/9
 */
public class MultiResponse<T> extends Response{
    private static final long serialVersionUID = 1L;
    /**
     * 数据
     */
    private List<T> data;

    public List<T> getData() {
        // 返回不能为 null,设置为空集合
        return null == data ? Collections.emptyList() :data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public boolean isEmpty() {
        return null == data || data.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public static MultiResponse buildSuccess() {
        MultiResponse response = new MultiResponse();
        response.setSuccess(true);
        return response;
    }

    public static MultiResponse buildFailure(String errCode, String errMessage) {
        MultiResponse response = new MultiResponse();
        response.setSuccess(false);
        response.setErrCode(errCode);
        response.setErrMessage(errMessage);
        return response;
    }

    public static <T> MultiResponse<T> of(List<T> data) {
        MultiResponse<T> response = new MultiResponse<>();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }
}
