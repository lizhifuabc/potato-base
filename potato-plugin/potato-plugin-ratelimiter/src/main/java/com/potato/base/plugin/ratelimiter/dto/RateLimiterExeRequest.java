package com.potato.base.plugin.ratelimiter.dto;

import com.potato.base.plugin.ratelimiter.enums.AlgorithmType;

/**
 * 算法
 *
 * @author lizhifu
 * @date 2021/12/10
 */
public class RateLimiterExeRequest {
    /**
     * 请求标识
     */
    private String key;
    /**
     * 每秒能够通过的请求数(令牌数)
     */
    private double replenishRate;
    /**
     * 最大容量
     */
    private double burstCapacity;
    /**
     * 请求数量
     */
    private double requestCount;
    /**
     * 算法名称
     */
    private AlgorithmType algorithmType;
    private RateLimiterExeRequest(Builder builder) {
        this.key = builder.key;
        this.replenishRate = builder.replenishRate;
        this.burstCapacity = builder.burstCapacity;
        this.requestCount = builder.requestCount;
        this.algorithmType = builder.algorithmType;
    }

    public String getKey() {
        return key;
    }

    public double getReplenishRate() {
        return replenishRate;
    }

    public double getBurstCapacity() {
        return burstCapacity;
    }

    public double getRequestCount() {
        return requestCount;
    }

    public AlgorithmType getAlgorithmType() {
        return algorithmType;
    }

    /**
     * Builder
     */
    public static class Builder {
        /**
         * 算法名称
         */
        private AlgorithmType algorithmType;
        /**
         * 请求标识
         */
        private String key;
        /**
         * 每秒能够通过的请求数(令牌数)
         */
        private double replenishRate;
        /**
         * 最大容量
         */
        private double burstCapacity;
        private double requestCount;

        public Builder (AlgorithmType algorithmType,String key) {
            this.algorithmType = algorithmType;
            this.key = key;
        }
        public Builder withBurstCapacity(double burstCapacity) {
            this.burstCapacity = burstCapacity;
            return this;
        }
        public Builder withReplenishRate(double replenishRate) {
            this.replenishRate = replenishRate;
            return this;
        }
        public Builder withRequestCount(double requestCount) {
            this.requestCount = requestCount;
            return this;
        }
        public RateLimiterExeRequest build() {
            return new RateLimiterExeRequest(this);
        }
    }
}
