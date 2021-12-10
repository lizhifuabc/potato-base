package com.potato.base.plugin.ratelimiter;

import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import com.potato.base.plugin.ratelimiter.enums.AlgorithmType;

/**
 * Algorithm
 *
 * @author lizhifu
 * @date 2021/12/10
 */
public class AlgorithmTest {
    public static void main(String[] args) {
        RateLimiterExeRequest rateLimiterExeRequest = new RateLimiterExeRequest.Builder(AlgorithmType.TOKEN_BUCKET,"demo")
                .build();
        System.out.println(rateLimiterExeRequest.getAlgorithmType().toString());
    }
}
