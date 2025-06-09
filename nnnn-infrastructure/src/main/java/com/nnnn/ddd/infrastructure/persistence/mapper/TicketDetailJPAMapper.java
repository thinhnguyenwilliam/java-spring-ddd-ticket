package com.nnnn.ddd.infrastructure.persistence.mapper;

import com.nnnn.ddd.domain.model.entity.TicketDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketDetailJPAMapper extends JpaRepository<TicketDetail, Long> {

    // Optional, as JpaRepository already has this method
    //It's already inherited from JpaRepository, so there's no need to redeclare it unless you're customizing behavior.
    //Optional<TicketDetail> findById(Long id);



}
