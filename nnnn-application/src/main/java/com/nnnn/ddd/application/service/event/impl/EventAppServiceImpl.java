package com.nnnn.ddd.application.service.event.impl;

import com.nnnn.ddd.application.service.event.EventAppService;
import com.nnnn.ddd.domain.service.HiDomainService;
import org.springframework.stereotype.Service;


@Service
public class EventAppServiceImpl implements EventAppService
{
    private final HiDomainService  hiDomainService;

    public EventAppServiceImpl(HiDomainService  hiDomainService){
        this.hiDomainService = hiDomainService;
    }

    @Override
    public String sayHi(String name) {
        return hiDomainService.sayHi(name);
    }
}
