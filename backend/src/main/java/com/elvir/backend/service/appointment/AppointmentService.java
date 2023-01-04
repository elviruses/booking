package com.elvir.backend.service.appointment;

import com.elvir.backend.model.dto.FreeTimeDto;

import java.util.UUID;

public interface AppointmentService {

    FreeTimeDto get(UUID employeeUUID, UUID serviceUUID);

}
