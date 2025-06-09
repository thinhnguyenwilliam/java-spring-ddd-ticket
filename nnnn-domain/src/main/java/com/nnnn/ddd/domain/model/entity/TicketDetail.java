package com.nnnn.ddd.domain.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_item")
public class TicketDetail {

    @Id
    private Long id;

    private String name;
    private String description;

    private int stockInitial;
    private int stockAvailable;

    private boolean isStockPrepared;

    private Long priceOriginal;
    private Long priceFlash;

    private LocalDateTime saleStartTime;
    private LocalDateTime saleEndTime;

    private int status;
    private Long activityId;

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;

}
