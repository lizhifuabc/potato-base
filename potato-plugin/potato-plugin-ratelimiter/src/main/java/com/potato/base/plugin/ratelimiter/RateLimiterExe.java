package com.potato.base.plugin.ratelimiter;

import com.potato.base.plugin.ratelimiter.algorithm.RateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.algorithm.RateLimiterAlgorithmFactory;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;

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

    public RateLimiterExeResponse exe(RateLimiterExeRequest rateLimiterExeRequest){
        log.info("RateLimiterExe exe algorithm:{}", rateLimiterExeRequest);
        RateLimiterAlgorithm rateLimiterAlgorithm = RateLimiterAlgorithmFactory.getRateLimiterAlgorithm(rateLimiterExeRequest.getAlgorithmType());

        double replenishRate = rateLimiterExeRequest.getReplenishRate();
        double burstCapacity = rateLimiterExeRequest.getBurstCapacity();
        double requestCount = rateLimiterExeRequest.getRequestCount();

        List<String> keys = rateLimiterAlgorithm.getKeys(rateLimiterExeRequest);
        log.info("RateLimiterExe exe keys:{}",keys);

        DefaultRedisScript<List<Long>> script = rateLimiterAlgorithm.getScript();

        long seconds = Instant.now().getEpochSecond();
        log.info("RateLimiterExe exe args: {} {} {} {}",replenishRate,burstCapacity,seconds,requestCount);

        // capacity str "3.0" capacity str type string
        // capacity str 3.0 capacity str type string
        List<Long> execute = stringRedisTemplate.execute(
                script,
                keys,
                String.valueOf(replenishRate),
                String.valueOf(burstCapacity),
                String.valueOf(seconds),
                String.valueOf(requestCount));

        log.info("RateLimiterExe exe result:{}",execute);

        RateLimiterExeResponse response = new RateLimiterExeResponse();
        response.setAllowed(execute.get(0) == 1L);
        response.setLeft(execute.get(1));

        return response;
    }
}
