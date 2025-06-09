package com.nnnn.ddd.infrastructure.cache.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
        //log.info("Set redis::1, {}", key);
        if (!StringUtils.hasLength(key)) {
            //log.info("Set redis::null, {}", StringUtils.hasLength(key));
            return;
        }
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.error("setObject error: {}", e.getMessage(), e);
        }
        //        redisTemplate.opsForValue().set(key, value);
//        // Kiểm tra xem giá trị có được lưu thành công hay không
//        Object result = redisTemplate.opsForValue().get(key);
//        log.info("Set redis::{}", result != null && result.equals(value));
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
