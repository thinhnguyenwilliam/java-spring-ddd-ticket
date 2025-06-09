package com.nnnn.ddd.domain.service.impl;

import com.nnnn.ddd.domain.repository.HiDomainRepository;
import com.nnnn.ddd.domain.service.HiDomainService;
import org.springframework.stereotype.Service;


@Service
public class HiDomainServiceImpl implements HiDomainService
{
    private final HiDomainRepository hiDomainRepository;

    public HiDomainServiceImpl(HiDomainRepository hiDomainRepository){
        this.hiDomainRepository = hiDomainRepository;
    }

    @Override
    public String sayHi(String name) {
        return hiDomainRepository.sayHi(name);
    }
}
