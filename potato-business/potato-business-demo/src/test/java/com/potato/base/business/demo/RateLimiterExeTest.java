package com.potato.base.business.demo;

import com.potato.base.plugin.ratelimiter.RateLimiterExe;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeResponse;
import com.potato.base.plugin.ratelimiter.enums.AlgorithmType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * RateLimiterExeTest
 *
 * @author lizhifu
 * @date 2021/12/13
 */
@SpringBootTest
public class RateLimiterExeTest {
    @Resource
    private RateLimiterExe rateLimiterExe;
    @Test
    public void concurrent(){
        RateLimiterExeRequest rateLimiterExeRequest = new RateLimiterExeRequest.Builder(AlgorithmType.CONCURRENT,"demo")
                .withBurstCapacity(1)
                .build();
        rateLimiterExe.exe(rateLimiterExeRequest);
    }
    @Test
    public void token_bucket(){
        RateLimiterExeRequest rateLimiterExeRequest = new RateLimiterExeRequest.Builder(AlgorithmType.TOKEN_BUCKET,"demo")
                .withBurstCapacity(20)
                .withRequestCount(30)
                .withReplenishRate(10)
                .build();
        rateLimiterExe.exe(rateLimiterExeRequest);
    }
    @Test
    public void sliding_window(){
        RateLimiterExeRequest rateLimiterExeRequest = new RateLimiterExeRequest.Builder(AlgorithmType.SLIDING_WINDOW,"demo")
                .withBurstCapacity(20)
                .withReplenishRate(10)
                .build();
        for (int i = 0; i < 20; i++) {
            RateLimiterExeResponse rateLimiterExeResponse = rateLimiterExe.exe(rateLimiterExeRequest);
            System.out.println("第" + i +"次操作" + (rateLimiterExeResponse.isAllowed() ? "成功" : "失败"));
        }
    }
    @Test
    public void leaky_bucke(){
        RateLimiterExeRequest rateLimiterExeRequest = new RateLimiterExeRequest.Builder(AlgorithmType.LEAKY_BUCKET,"demo")
                .withBurstCapacity(20)
                .withReplenishRate(10)
                .build();
        for (int i = 0; i < 20; i++) {
            RateLimiterExeResponse rateLimiterExeResponse = rateLimiterExe.exe(rateLimiterExeRequest);
            System.out.println("第" + i +"次操作" + (rateLimiterExeResponse.isAllowed() ? "成功" : "失败"));
        }
    }
}
