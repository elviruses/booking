package com.elvir.backend.controller;

import com.elvir.backend.model.dto.FreeTimeDto;
import com.elvir.backend.service.appointment.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/appointment/{employee_id}/{service_id}")
    public FreeTimeDto get(@PathVariable("employee_id") UUID uuidEmployee, @PathVariable("service_id") UUID uuidService) {
        return appointmentService.get(uuidEmployee, uuidService);
    }
}
