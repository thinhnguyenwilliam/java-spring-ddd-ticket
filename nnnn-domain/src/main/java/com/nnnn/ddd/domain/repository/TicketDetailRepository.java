package com.nnnn.ddd.domain.repository;

import com.nnnn.ddd.domain.model.entity.TicketDetail;

import java.util.Optional;

public interface TicketDetailRepository {

    Optional<TicketDetail> findById(Long id);
}
