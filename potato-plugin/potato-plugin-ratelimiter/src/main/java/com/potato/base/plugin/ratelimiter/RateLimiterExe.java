package com.potato.base.plugin.ratelimiter;

import com.potato.base.plugin.ratelimiter.algorithm.RateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.algorithm.RateLimiterAlgorithmFactory;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 限流执行
 *
 * @author lizhifu
 * @date 2021/12/10
 */
@Slf4j
@Service
public class RateLimiterExe {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void exe(RateLimiterExeRequest rateLimiterExeRequest){
        log.info("RateLimiterExe exe algorithm:{}", rateLimiterExeRequest);
        RateLimiterAlgorithm rateLimiterAlgorithm = RateLimiterAlgorithmFactory.getRateLimiterAlgorithm(rateLimiterExeRequest.getAlgorithmType());
    }
}
