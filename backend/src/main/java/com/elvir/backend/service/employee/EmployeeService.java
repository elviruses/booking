package com.elvir.backend.service.employee;

import com.elvir.backend.model.dto.EmployeeDto;
import com.elvir.backend.model.request.EmployeeInfo;

import java.util.List;
import java.util.UUID;

public interface EmployeeService {

    void create(EmployeeInfo employeeInfo);

    void update(UUID uuid, EmployeeInfo employeeInfo);

    void delete(UUID uuid);

    EmployeeDto get(UUID uuid);

    List<EmployeeDto> getList();

    void addSkill(UUID uuid, List<UUID> servicingUUIDs);
}
