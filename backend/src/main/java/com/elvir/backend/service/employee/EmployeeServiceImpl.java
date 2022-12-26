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
        employeeRepository.save(filledEmployee(employee, employeeInfo));
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
        List<Servicing> servicings = servicingRepository.findAllById(servicingUUIDs);
        employee.getSkills().addAll(servicings);
        employeeRepository.save(employee);
    }

    private Employee filledEmployee(Employee employee, EmployeeInfo employeeInfo) {
        employee.setFirstName(employeeInfo.getFirstName());
        employee.setLastName(employeeInfo.getLastName());
        employee.setBirthday(employeeInfo.getBirthday());
        employee.setStartTime(employeeInfo.getStartTime());
        employee.setEndTime(employeeInfo.getEndTime());
        return employee;
    }

    private List<Appointment> generateAppointmentListForEmployee(Employee employee) {
        List<Appointment> appointmentList = new ArrayList<>();

        int countDay = 14;
        int countWorkDay = employee.getWorkDays();
        int countDayOff = employee.getOffDays();

        LocalDate currentDay = LocalDate.now();

        for (int i = 0; i < countDay; i++) {
            if (countWorkDay != 0) {
                countWorkDay--;
                Appointment appointment = Appointment.builder()
                        .type(TypeAppointment.SERVICE)
                        .employee(employee)
                        .startTime(currentDay.atTime(employee.getLunchStart()))
                        .endTime(currentDay.atTime(employee.getLunchEnd()))
                        .build();
                appointmentList.add(appointment);
                if (countWorkDay == 0) {
                    countDayOff = employee.getOffDays();
                }
            } else if (countDayOff != 0) {
                countDayOff--;
                Appointment appointment = Appointment.builder()
                        .type(TypeAppointment.SERVICE)
                        .employee(employee)
                        .startTime(currentDay.atTime(employee.getStartTime()))
                        .endTime(currentDay.atTime(employee.getEndTime()))
                        .build();
                appointmentList.add(appointment);
                if (countDayOff == 0) {
                    countWorkDay = employee.getWorkDays();
                }
            }
            currentDay = currentDay.plusDays(1);
        }

        return appointmentList;
    }
}
