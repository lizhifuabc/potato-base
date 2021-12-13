package com.potato.base.plugin.ratelimiter.dto;

import lombok.Data;

/**
 * 限流执行结果
 *
 * @author lizhifu
 * @date 2021/12/3
 */
@Data
public class RateLimiterExeResponse {
    /**
     * 是否通过
     */
    private boolean allowed;
    /**
     * 剩余次数
     */
    private long left;
}
