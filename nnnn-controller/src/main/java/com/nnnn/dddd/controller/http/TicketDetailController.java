package com.nnnn.dddd.controller.http;


import com.nnnn.ddd.application.service.ticket.TicketDetailAppService;
import com.nnnn.ddd.domain.model.entity.TicketDetail;
import com.nnnn.dddd.controller.model.enums.ResultUtil;
import com.nnnn.dddd.controller.model.vo.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketDetailController {

    // ✅ Injected via constructor
    private final TicketDetailAppService ticketDetailAppService;

    // ✅ Constructor for injection (required for Spring)
    public TicketDetailController(TicketDetailAppService ticketDetailAppService) {
        this.ticketDetailAppService = ticketDetailAppService;
    }

    @GetMapping("/{ticketId}/detail/{detailId}")
    public ResultMessage<TicketDetail> getTicketDetail(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("detailId") Long detailId
    ) {
        log.info("MEMBER TIPS GO");
        log.info("ticketId: {}, detailId: {}", ticketId, detailId);
        return ResultUtil.data(ticketDetailAppService.getTicketDetailById(detailId));
    }
}
