package com.pet.adoption.po;

import com.pet.adoption.enums.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Animal {
    @Id
    private String id;
    private String name;
    @Column(length = 2048)
    private String description;
    private String image;
    private String category;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime updatedAt;
}
