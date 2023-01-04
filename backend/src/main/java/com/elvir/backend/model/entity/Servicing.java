package com.elvir.backend.model.entity;

import com.elvir.backend.model.request.ServicingInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "services")
@EqualsAndHashCode(of = "id")
@ToString
public class Servicing {

    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name_service", nullable = false)
    private String nameService;

    @Column(name = "description")
    private String description;

    @Column(name = "time_service", nullable = false)
    private Integer timeService;

    @ManyToMany
    @JoinTable(
            name = "employee_skill",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employeeSet;

    public void updateByServicingInfo(ServicingInfo servicingInfo) {
        this.nameService = servicingInfo.getNameService();
        this.description = servicingInfo.getDescription();
        this.timeService = servicingInfo.getTimeService();
    }
}
