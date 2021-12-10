package com.potato.base.plugin.ratelimiter.algorithm;

import org.springframework.data.redis.core.script.DefaultRedisScript;

/**
 * 限流算法
 *
 * @author lizhifu
 * @date 2021/12/10
 */
public interface RateLimiterAlgorithm<T> {
    /**
     * 脚本名称
     * @return
     */
    public String getScriptName();
    /**
     * redis 脚本
     * @return
     */
    public DefaultRedisScript<T> getScript();
}
