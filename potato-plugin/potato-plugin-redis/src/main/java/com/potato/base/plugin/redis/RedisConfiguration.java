package com.potato.base.plugin.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

/**
 * Redis 配置
 *
 * @author lizhifu
 * @date 2021/12/10
 */
@Configuration
@AutoConfigureAfter({RedisAutoConfiguration.class})
@Slf4j
public class RedisConfiguration {
    @Resource
    private LettuceConnectionFactory lettuceConnectionFactory;

    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new Jackson2JsonRedisSerializer<>(Object.class);
    }
    /**
     * redisTemplate
     * @return
     */
    @Bean(name = "redisTemplate")
    @ConditionalOnMissingBean(RedisTemplate.class)
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        // key 的序列化采用StringRedisSerializer
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());

        // value 的序列化采用 FastJsonRedisSerializer [包括 String 类型和 Hash 类型]
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());

        redisTemplate.setDefaultSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();
        log.info("RedisTemplate redisTemplate config");
        return redisTemplate;
    }

    /**
     * stringRedisTemplate
     * @return
     */
    @Bean(name = "stringRedisTemplate")
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        stringRedisTemplate.afterPropertiesSet();
        log.info("RedisTemplate stringRedisTemplate config");
        return stringRedisTemplate;
    }
}
