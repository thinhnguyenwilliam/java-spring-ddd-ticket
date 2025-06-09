package com.nnnn.ddd.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {

    @Id
    private Long id;

    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private int status;
    private Date updatedAt;
    private Date createdAt;

}
