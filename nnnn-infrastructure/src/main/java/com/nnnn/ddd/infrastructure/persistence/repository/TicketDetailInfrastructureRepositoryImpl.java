package com.nnnn.ddd.infrastructure.persistence.repository;

import com.nnnn.ddd.domain.model.entity.TicketDetail;
import com.nnnn.ddd.domain.repository.TicketDetailRepository;
import com.nnnn.ddd.infrastructure.persistence.mapper.TicketDetailJPAMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TicketDetailInfrastructureRepositoryImpl implements TicketDetailRepository {

    private final TicketDetailJPAMapper ticketDetailJPAMapper;

    // ✅ Add constructor for dependency injection
    public TicketDetailInfrastructureRepositoryImpl(TicketDetailJPAMapper ticketDetailJPAMapper) {
        this.ticketDetailJPAMapper = ticketDetailJPAMapper;
    }

    @Override
    public Optional<TicketDetail> findById(Long id) {
        log.info("Infrastructure: fetching TicketDetail by ID = {}", id);
        return ticketDetailJPAMapper.findById(id);
    }
}
