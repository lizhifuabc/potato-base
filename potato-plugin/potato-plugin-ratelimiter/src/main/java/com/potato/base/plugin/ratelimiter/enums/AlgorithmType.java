package com.potato.base.plugin.ratelimiter.enums;

import com.potato.base.plugin.ratelimiter.algorithm.RateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.algorithm.impl.ConcurrentRateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.algorithm.impl.LeakyBucketRateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.algorithm.impl.SlidingWindowRateLimiterAlgorithm;
import com.potato.base.plugin.ratelimiter.algorithm.impl.TokenBucketRateLimiterAlgorithm;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Supplier;

/**
 * 算法类型
 *
 * @author lizhifu
 * @date 2021/12/10
 */
@RequiredArgsConstructor
@Getter
public enum AlgorithmType {
    /**
     * 防止并发
     */
    CONCURRENT(ConcurrentRateLimiterAlgorithm::new),
    /**
     * 滑动窗口
     */
    SLIDING_WINDOW(SlidingWindowRateLimiterAlgorithm::new),
    /**
     * 漏桶
     */
    LEAKY_BUCKET(LeakyBucketRateLimiterAlgorithm::new),
    /**
     * 令牌桶
     */
    TOKEN_BUCKET(TokenBucketRateLimiterAlgorithm::new);

    private final Supplier<RateLimiterAlgorithm> constructor;
}
