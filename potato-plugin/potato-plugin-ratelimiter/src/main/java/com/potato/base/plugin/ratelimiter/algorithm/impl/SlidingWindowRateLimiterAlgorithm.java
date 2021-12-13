package com.potato.base.plugin.ratelimiter.algorithm.impl;

import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * 滑动窗口算法
 * 1. zset 结构，score 为毫秒时间戳，value 为随机数
 * 2. 只保留窗口时间内数据，当前时间 - （窗口大小&令牌桶大小 / 窗口时间&1秒）
 * 3. 窗口大小&令牌桶大小作为 key 的过期时间
 * @author lizhifu
 * @date 2021/12/13
 */
public class SlidingWindowRateLimiterAlgorithm extends AbstractRateLimiterAlgorithm{
    @Override
    public String getScriptName() {
        return "sliding_window_rate_limiter.lua";
    }
    @Override
    public List<String> getKeys(RateLimiterExeRequest rateLimiterExeRequest) {
        String prefix = rateLimiterExeRequest.getAlgorithmType().toString() + ".{" + rateLimiterExeRequest.getKey()+"}";
        String tokenKey = prefix + ".token";
        String timestampKey = UUID.randomUUID().toString();
        return Arrays.asList(tokenKey, timestampKey);
    }
}
