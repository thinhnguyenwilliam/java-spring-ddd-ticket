package com.nnnn.ddd.application.service.ticket.impl;

import com.nnnn.ddd.application.service.ticket.TicketDetailAppService;
import com.nnnn.ddd.application.service.ticket.cache.TicketDetailCacheService;
import com.nnnn.ddd.domain.model.entity.TicketDetail;
import com.nnnn.ddd.domain.service.TicketDetailDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TicketDetailAppServiceImpl implements TicketDetailAppService {

    private final TicketDetailDomainService ticketDetailDomainService;
    private final TicketDetailCacheService ticketDetailCacheService;

    // Constructor injection ensures Spring injects these beans
    public TicketDetailAppServiceImpl(TicketDetailDomainService ticketDetailDomainService,
                                      TicketDetailCacheService ticketDetailCacheService) {
        this.ticketDetailDomainService = ticketDetailDomainService;
        this.ticketDetailCacheService = ticketDetailCacheService;
    }

    @Override
    public TicketDetail getTicketDetailById(Long ticketId) {
        log.info("Implement Application : {}", ticketId);
        // You can switch between these depending on your cache strategy
        // return ticketDetailDomainService.getTicketDetailById(ticketId);
        return ticketDetailCacheService.getTicketDefaultCacheNormal(ticketId, System.currentTimeMillis());
        //return ticketDetailCacheService.getTicketDefaultCacheVip(ticketId, System.currentTimeMillis());
    }
}


