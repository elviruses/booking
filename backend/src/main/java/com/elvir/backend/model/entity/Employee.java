package com.elvir.backend.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
@EqualsAndHashCode(of = "id")
@ToString
public class Employee {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "lunch_start", nullable = false)
    private LocalTime lunchStart;

    @Column(name = "lunch_end", nullable = false)
    private LocalTime lunchEnd;

    @Column(name = "work_days", nullable = false)
    private Integer workDays;

    @Column(name = "off_days", nullable = false)
    private Integer offDays;

    @ManyToMany(mappedBy = "employeeSet")
    private Set<Servicing> skills;
}
