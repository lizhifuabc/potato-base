package com.potato.base.plugin.redission;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * RedissonConfig
 *
 * @author lizhifu
 * @date 2021/12/15
 */
@Configuration
public class RedissonConfig {
    @Bean
    public RedissonClient redisson() throws IOException {
        // 两种读取方式，Config.fromYAML 和 Config.fromJSON
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource("redisson-config.yml"));
        return Redisson.create(config);
    }
}
