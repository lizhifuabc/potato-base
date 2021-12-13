package com.potato.base.plugin.ratelimiter.algorithm.impl;

/**
 * 漏桶算法: 水（请求）先进入到漏桶里，漏桶以一定的速度出水，当水流入速度过大会直接溢出，强行限制数据的传输速率。
 *
 * @author lizhifu
 * @date 2021/12/13
 */
public class LeakyBucketRateLimiterAlgorithm extends AbstractRateLimiterAlgorithm{
    @Override
    public String getScriptName() {
        return "request_leaky_rate_limiter.lua";
    }
}
