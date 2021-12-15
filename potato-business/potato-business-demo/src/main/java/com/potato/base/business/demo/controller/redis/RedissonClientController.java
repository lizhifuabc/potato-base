package com.potato.base.business.demo.controller.redis;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RedissonClientController
 *
 * @author lizhifu
 * @date 2021/12/15
 */
@RestController
@RequestMapping("redisson")
public class RedissonClientController {
    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/set/{key}")
    public String s1(@PathVariable String key) {
        // 设置字符串
        RBucket<String> keyObj = redissonClient.getBucket(key);
        keyObj.set(key);

        // 获取字符串
        String str = keyObj.get();
        return str;
    }
}
