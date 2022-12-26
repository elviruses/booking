package com.elvir.backend.service.appointment;

import com.elvir.backend.model.dto.AppointmentDto;

import java.util.UUID;

public interface AppointmentService {

    AppointmentDto get(UUID employeeUUID, UUID serviceUUID);

}
