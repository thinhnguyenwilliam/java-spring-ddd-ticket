package com.nnnn.ddd.application.service.ticket.cache;


import com.nnnn.ddd.domain.model.entity.TicketDetail;
import com.nnnn.ddd.domain.service.TicketDetailDomainService;
import com.nnnn.ddd.infrastructure.cache.redis.RedisInfrastructureService;
import com.nnnn.ddd.infrastructure.distributed.redisson.RedisDistributedLocker;
import com.nnnn.ddd.infrastructure.distributed.redisson.RedisDistributedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TicketDetailCacheService {

    private final RedisDistributedService redisDistributedService;
    private final RedisInfrastructureService redisInfraService;
    private final TicketDetailDomainService ticketDetailDomainService;

    // Constructor injection of dependencies
    public TicketDetailCacheService(
            RedisDistributedService redisDistributedService,
            RedisInfrastructureService redisInfraService,
            TicketDetailDomainService ticketDetailDomainService) {
        this.redisDistributedService = redisDistributedService;
        this.redisInfraService = redisInfraService;
        this.ticketDetailDomainService = ticketDetailDomainService;
    }

    public TicketDetail getTicketDefaultCacheNormal(Long id, Long version) {
        TicketDetail ticketDetail = redisInfraService.getObject(genEventItemKey(id), TicketDetail.class);
        if (ticketDetail != null) {
            log.info("FROM CACHE {}, {}, {}", id, version, ticketDetail);
            return ticketDetail;
        }

        ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
        log.info("FROM DBS {}, {}, {}", id, version, ticketDetail);

        if (ticketDetail != null) {
            redisInfraService.setObject(genEventItemKey(id), ticketDetail);
        }
        return ticketDetail;
    }

    public TicketDetail getTicketDefaultCacheVip(Long id, Long version) {
        log.info("Implement getTicketDefaultCacheVip->, {}, {} ", id, version);
        TicketDetail ticketDetail = ticketDetailDomainService.getTicketDetailById(id);

        if (ticketDetail != null) {
            return ticketDetail;
        }

        RedisDistributedLocker locker = redisDistributedService.getDistributedLock("PRO_LOCK_KEY_ITEM" + id);
        try {
            boolean isLock = locker.tryLock(1, 5, TimeUnit.SECONDS);
            if (!isLock) {
                return ticketDetail;
            }
            ticketDetail = redisInfraService.getObject(genEventItemKey(id), TicketDetail.class);
            if (ticketDetail != null) {
                return ticketDetail;
            }

            ticketDetail = ticketDetailDomainService.getTicketDetailById(id);
            log.info("FROM DBS ->>>> {}, {}", ticketDetail, version);
            if (ticketDetail == null) {
                log.info("TICKET NOT EXISTS....{}", version);
                redisInfraService.setObject(genEventItemKey(id), ticketDetail);
                return ticketDetail;
            }

            redisInfraService.setObject(genEventItemKey(id), ticketDetail);
            return ticketDetail;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            locker.unlock();
        }
    }

    private String genEventItemKey(Long itemId) {
        return "PRO_TICKET:ITEM:" + itemId;
    }
}
