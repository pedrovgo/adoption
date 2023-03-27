package com.pet.adoption.po;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class DataLoader {
    @Id
    private String id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean finished;
    @Column(length = 2048)
    private String errorMessage;
}
