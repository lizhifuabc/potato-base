package com.potato.base.plugin.ratelimiter.algorithm.impl;

/**
 * 令牌桶算法
 * 容易一下子被请求打死
 * @author lizhifu
 * @date 2021/12/8
 */
public class TokenBucketRateLimiterAlgorithm extends AbstractRateLimiterAlgorithm{
    @Override
    public String getScriptName() {
        return "token_bucket_rate_limiter.lua";
    }
}