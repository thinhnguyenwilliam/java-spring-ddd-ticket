package com.nnnn.dddd.controller.http;

import com.nnnn.ddd.application.service.event.EventAppService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HiController {

    private final EventAppService eventAppService;
    private final RestTemplate  restTemplate;

    public HiController(EventAppService eventAppService, RestTemplate  restTemplate) {
        this.eventAppService = eventAppService;
        this.restTemplate = restTemplate;
    }

//    /actuator/health	Application health, including circuitBreakers and rateLimiters
//    /actuator/circuitbreakers	Status of all Resilience4j circuit breakers
//      /actuator/ratelimiters	Current state of rate limiters
//      /actuator/metrics

    @GetMapping("/circuit/breaker")
    @CircuitBreaker(name = "checkRandom", fallbackMethod = "fallbackForExternalCall")
    public Map<String, Object> circuitBreaker() {
        SecureRandom secureRandom = new SecureRandom();
        int productId = secureRandom.nextInt(20) + 1;
        String url = "https://fakestoreapi.com/products/" + productId;

        Object product = restTemplate.getForObject(url, Object.class);

        Map<String, Object> result = new HashMap<>();
        result.put("productId", productId);
        result.put("data", product);
        return result;
    }

    public Map<String, Object> fallbackForExternalCall(Throwable t) {
        log.error("Circuit breaker fallback triggered", t);
        Map<String, Object> fallback = new HashMap<>();
        fallback.put("message Circuit breaker", "External service unavailable - fallback response");
        return fallback;
    }

    @GetMapping("/hi")
    @RateLimiter(name = "backendA", fallbackMethod = "rateLimitFallback")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", eventAppService.sayHi("world cup"));
        return response;
    }

    // 👇 JSON fallback method
    public Map<String, String> rateLimitFallback(Throwable ex) {
        log.error("Rate limit triggered: {}", ex.getMessage());

        Map<String, String> fallbackResponse = new HashMap<>();
        fallbackResponse.put("message", "Too many requests - try again later.");
        return fallbackResponse;
    }

}
