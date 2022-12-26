package com.elvir.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "free_time")
@EqualsAndHashCode(of = "id")
@ToString
public class FreeTime {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable = false)
    private Employee employee;

    @Column(name = "free_time", nullable = false)
    private LocalDateTime freeTime;
}
