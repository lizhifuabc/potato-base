package com.potato.base.plugin.ratelimiter.algorithm;

import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.List;

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

    /**
     * 获取 redis key
     * @param rateLimiterExeRequest 限流请求参数
     * @return
     */
    List<String> getKeys(RateLimiterExeRequest rateLimiterExeRequest);
}
