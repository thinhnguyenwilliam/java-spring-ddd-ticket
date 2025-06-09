package com.nnnn.dddd.controller.resource;

import com.nnnn.ddd.application.service.event.EventAppService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hello")
public class HiController {

    private final EventAppService eventAppService;

    // ✅ Constructor injection (Spring will autowire this)
    public HiController(EventAppService eventAppService) {
        this.eventAppService = eventAppService;
    }

    @GetMapping
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", eventAppService.sayHi("world cup"));
        return response;
    }
}
