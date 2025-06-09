package com.nnnn.ddd.infrastructure.distributed.redisson.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {


    @Bean(destroyMethod = "shutdown")  // Properly shut down RedissonClient on app close
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setConnectionPoolSize(50)
                .setDatabase(0);

        return Redisson.create(config);
    }
}

