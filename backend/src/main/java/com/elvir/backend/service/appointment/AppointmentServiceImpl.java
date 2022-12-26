package com.elvir.backend.service.appointment;

import com.elvir.backend.model.dto.AppointmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    @Override
    public AppointmentDto get(UUID employeeUUID, UUID serviceUUID) {
        return null;
    }
}
