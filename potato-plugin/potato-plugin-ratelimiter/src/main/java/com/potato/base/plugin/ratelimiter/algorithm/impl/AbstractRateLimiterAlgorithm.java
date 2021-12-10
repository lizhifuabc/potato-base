package com.potato.base.plugin.ratelimiter.algorithm.impl;

import com.potato.base.plugin.ratelimiter.algorithm.RateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.Arrays;
import java.util.List;

/**
 * 限流算法抽象实现
 *
 * @author lizhifu
 * @date 2021/12/10
 */
public abstract class AbstractRateLimiterAlgorithm implements RateLimiterAlgorithm<List<Long>>{
    /**
     * 脚本存放位置
     */
    private final String SCRIPT_PATH = "/META-INF/lua/";
    /**
     * redis 脚本
     */
    private final DefaultRedisScript<List<Long>> script;

    public AbstractRateLimiterAlgorithm() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        String scriptPath = SCRIPT_PATH + getScriptName();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(scriptPath)));
        redisScript.setResultType(List.class);
        this.script = redisScript;
    }
    @Override
    public List<String> getKeys(RateLimiterExeRequest rateLimiterExeRequest) {
        // 前缀{key} 集群下通过{}保障
        String prefix = rateLimiterExeRequest.getAlgorithmType().toString() + ".{" + rateLimiterExeRequest.getKey()+"}";
        // token
        String tokenKey = prefix + ".token";
        // 时间戳
        String timestampKey = prefix + ".timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }
    @Override
    public DefaultRedisScript<List<Long>> getScript(){
        return this.script;
    }
}
