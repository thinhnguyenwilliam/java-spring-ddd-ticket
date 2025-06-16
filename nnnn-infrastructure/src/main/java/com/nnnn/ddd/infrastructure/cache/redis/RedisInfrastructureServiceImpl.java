package com.nnnn.ddd.infrastructure.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Optional;

@Component
@Slf4j
public class RedisInfrastructureServiceImpl implements RedisInfrastructureService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    public RedisInfrastructureServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void setString(String key, String value) {
        if (!StringUtils.hasLength(key)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String getString(String key) {
//        Object result = redisTemplate.opsForValue().get(key);
//        log.info(String.valueOf(result));
        return Optional.ofNullable(redisTemplate.opsForValue().get(key))
                .map(String::valueOf)
                .orElse(null);
    }

    @Override
    public void setObject(String key, Object value) {
        setObject(key, value, Duration.ofMinutes(10)); // Example: 10 minutes TTL
    }

    public void setObject(String key, Object value, Duration ttl) {
        if (!StringUtils.hasLength(key)) {
            return;
        }
        try {
            redisTemplate.opsForValue().set(key, value, ttl);
        } catch (Exception e) {
            log.error("setObject with TTL error: {}", e.getMessage(), e);
        }
    }


    @Override
    public <T> T getObject(String key, Class<T> targetClass) {
        Object result = redisTemplate.opsForValue().get(key);
        log.info("get Cache::{}", result);

        if (result == null) {
            return null;
        }
//        try {
//            log.info("get Cache::1{}", JSON.parseObject((String) result, targetClass));
//            return JSON.parseObject((String) result, targetClass);
//        } catch (Exception e) {
//            log.error("error Cache::{}", e);
//            return null;
//        }
        // Nếu kết quả là một LinkedHashMap

        try {
            if (result instanceof String str) {
                // Nếu result là String, thực hiện chuyển đổi bình thường
                return objectMapper.readValue(str, targetClass);
            }

            // Chuyển đổi LinkedHashMap thành đối tượng mục tiêu
            return objectMapper.convertValue(result, targetClass);
        } catch (JsonProcessingException e) {
            log.error("Error deserializing JSON to object: {}", e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.error("Error converting object to target class: {}", e.getMessage(), e);
        }

        return null; // hoặc ném ra một ngoại lệ tùy ý
    }
}
