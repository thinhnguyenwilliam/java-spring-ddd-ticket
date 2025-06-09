package com.nnnn.ddd.infrastructure.persistence.repository;

import com.nnnn.ddd.domain.repository.HiDomainRepository;
import org.springframework.stereotype.Service;


@Service
public class HiInfrastructureRepositoryImpl implements HiDomainRepository
{
    @Override
    public String sayHi(String name) {
        return "Hi Infrastructure " + name;
    }
}
