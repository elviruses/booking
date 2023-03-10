package com.elvir.backend.model.repo;

import com.elvir.backend.model.entity.Appointment;
import com.elvir.backend.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findAppointmentsByEmployeeAndStartTimeBetween(Employee employee, LocalDateTime startDay, LocalDateTime endDay);
}
