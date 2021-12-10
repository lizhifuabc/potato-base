package com.potato.base.plugin.ratelimiter.algorithm.impl;

import com.potato.base.plugin.ratelimiter.dto.RateLimiterExeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * 并发限流算法-单秒内最大请求次数
 * Redis 有序集合和集合一样也是 string 类型元素的集合,且不允许重复的成员。
 * 不同的是每个元素都会关联一个 double 类型的分数。redis 正是通过分数来为集合中的成员进行从小到大的排序。
 * 有序集合的成员是唯一的,但分数(score)却可以重复。
 * 集合是通过哈希表实现的，所以添加，删除，查找的复杂度都是 O(1)。 集合中最大的成员数为 232 - 1 (4294967295, 每个集合可存储40多亿个成员)。
 * 流程：
 * 1. 一秒内同时请求 100 次
 * 2. 此时 zset 内 score 为 1638946728，member为 100 个随机数
 * 3. 每执行一次请求，删除 member，保证了请求次数
 * @author lizhifu
 * @date 2021/12/3
 */
@Component
@Slf4j
public class ConcurrentRateLimiterAlgorithm extends AbstractRateLimiterAlgorithm{
    @Override
    public String getScriptName() {
        return "concurrent_request_rate_limiter.lua";
    }
    @Override
    public List<String> getKeys(RateLimiterExeRequest rateLimiterExeRequest) {
        // 前缀{key} 集群下通过{}保障
        String prefix = rateLimiterExeRequest.getAlgorithmType().toString() + ".{" + rateLimiterExeRequest.getKey()+"}";
        // token
        String tokenKey = prefix + ".token";
        // 随机
        String timestampKey = UUID.randomUUID().toString();
        return Arrays.asList(tokenKey, timestampKey);
    }
}
