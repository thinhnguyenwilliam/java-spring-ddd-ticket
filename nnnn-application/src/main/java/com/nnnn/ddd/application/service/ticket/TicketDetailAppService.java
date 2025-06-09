package com.nnnn.ddd.application.service.ticket;


import com.nnnn.ddd.domain.model.entity.TicketDetail;

public interface TicketDetailAppService {

    TicketDetail getTicketDetailById(Long ticketId); // should convert to TickDetailDTO by Application Module
}

