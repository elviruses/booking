package com.elvir.backend.service.employee;

import com.elvir.backend.model.dto.EmployeeDto;
import com.elvir.backend.model.entity.Appointment;
import com.elvir.backend.model.entity.Employee;
import com.elvir.backend.model.entity.Servicing;
import com.elvir.backend.model.enums.TypeAppointment;
import com.elvir.backend.model.mapper.EmployeeMapper;
import com.elvir.backend.model.repo.AppointmentRepository;
import com.elvir.backend.model.repo.EmployeeRepository;
import com.elvir.backend.model.repo.ServicingRepository;
import com.elvir.backend.model.request.EmployeeInfo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ServicingRepository servicingRepository;

    private final AppointmentRepository appointmentRepository;
    private final EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);

    @Override
    @Transactional
    public void create(EmployeeInfo employeeInfo) {
        Employee employee = MAPPER.employeeInfoToEmployee(employeeInfo);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void update(UUID uuid, EmployeeInfo employeeInfo) {
        Employee employee = employeeRepository.findById(uuid).orElseThrow(RuntimeException::new);
        employee.updateByEmployeeInfo(employeeInfo);
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        boolean existsEmployee = employeeRepository.existsById(uuid);
        if (existsEmployee) {
            employeeRepository.deleteById(uuid);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeDto get(UUID uuid) {
        Employee employee = employeeRepository.findById(uuid).orElseThrow(RuntimeException::new);
        return MAPPER.employeeToEmployeeDto(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDto> getList() {
        return employeeRepository.findAll().stream()
                .map(MAPPER::employeeToEmployeeDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addSkill(UUID uuid, List<UUID> servicingUUIDs) {
        Employee employee = employeeRepository.findById(uuid).orElseThrow(RuntimeException::new);
        List<Servicing> servicingList = servicingRepository.findAllById(servicingUUIDs);
        employee.getSkills().addAll(servicingList);
        employeeRepository.save(employee);
    }
}
