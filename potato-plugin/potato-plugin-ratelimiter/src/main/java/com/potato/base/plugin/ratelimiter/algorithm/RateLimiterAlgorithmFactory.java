package com.potato.base.plugin.ratelimiter.algorithm;

import com.potato.base.plugin.ratelimiter.enums.AlgorithmType;

/**
 * 算法工厂
 *
 * @author lizhifu
 * @date 2021/12/10
 */
public class RateLimiterAlgorithmFactory {
    /**
     * 获取算法
     * @param algorithmType
     * @return
     */
    public static RateLimiterAlgorithm getRateLimiterAlgorithm(AlgorithmType algorithmType){
        return algorithmType.getConstructor().get();
    }
}
