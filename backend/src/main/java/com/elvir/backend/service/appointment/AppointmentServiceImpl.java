package com.elvir.backend.service.appointment;

import com.elvir.backend.model.dto.FreeTimeDto;
import com.elvir.backend.model.entity.Appointment;
import com.elvir.backend.model.entity.Employee;
import com.elvir.backend.model.entity.Servicing;
import com.elvir.backend.model.repo.AppointmentRepository;
import com.elvir.backend.model.repo.EmployeeRepository;
import com.elvir.backend.model.repo.ServicingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final EmployeeRepository employeeRepository;
    private final ServicingRepository servicingRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    @Transactional(readOnly = true)
    public FreeTimeDto get(UUID employeeUUID, UUID serviceUUID) {
        final Set<Long> allAvailableTime = new HashSet<>();
        final LocalDate today = LocalDate.now();

        final Employee employee = employeeRepository.findById(employeeUUID).orElseThrow(RuntimeException::new);
        final Servicing servicing = servicingRepository.findById(serviceUUID).orElseThrow(RuntimeException::new);
        final List<Appointment> appointmentList = appointmentRepository.findAppointmentsByEmployeeAndStartTimeBetween(
                employee, today.atStartOfDay(), today.plusDays(7).atTime(23, 59)
        );

        for (int i = 0; i <= 7; i++) {
            LocalDate currentDate = LocalDate.now().plusDays(i);
            Set<Long> availableTime = getFreeTimeEmployeeByDate(currentDate, employee, appointmentList, servicing);
            allAvailableTime.addAll(availableTime);
        }

        return new FreeTimeDto(allAvailableTime);
    }

    private Set<Long> getFreeTimeEmployeeByDate(LocalDate localDate, Employee employee, List<Appointment> appointmentList,
                                                Servicing servicing) {
        final Set<Long> freeTimestamp = new HashSet<>();

        if (employee.getLunchStart() != null) {
            appointmentList.add(
                    Appointment.builder()
                            .startTime(LocalDateTime.of(localDate, employee.getLunchStart()))
                            .endTime(LocalDateTime.of(localDate, employee.getLunchEnd()))
                            .build()
            );
        }

        List<TimeSlot> appointmentTimeSlotList = appointmentList.stream()
                .filter(e -> e.getStartTime().toLocalDate().equals(localDate))
                .map(this::appointmentToTimeSlot)
                .sorted(Comparator.comparing(TimeSlot::getStartTime))
                .collect(Collectors.toList());

        List<TimeSlot> freeTimeSlotList = getFreeSlots(localDate, employee, appointmentTimeSlotList, servicing);

        for (TimeSlot timeSlot : freeTimeSlotList) {
            long start = timeSlot.getStartTime().toEpochSecond((ZoneOffset) ZoneId.systemDefault());
            long end = start + (timeSlot.getDuration() * 60);
            while (start <= end) {
                freeTimestamp.add(start);
                start += 300;
            }
        }

        return freeTimestamp;
    }

    private TimeSlot appointmentToTimeSlot(Appointment appointment) {
        final LocalDateTime start = appointment.getStartTime();
        final long duration = ChronoUnit.MINUTES.between(start, appointment.getEndTime());
        return new TimeSlot(start, duration);
    }

    private List<TimeSlot> getFreeSlots(LocalDate localDate, Employee employee, List<TimeSlot> timeSlots, Servicing servicing) {
        List<TimeSlot> out = new ArrayList<>();

        LocalDateTime startJobEmployee = LocalDateTime.of(localDate, employee.getStartTime());
        LocalDateTime endJobEmployee = LocalDateTime.of(localDate, employee.getEndTime());

        if (timeSlots.size() != 0) {
            TimeSlot prevElementTimeSlot = timeSlots.get(0);

            long startDuration = ChronoUnit.MINUTES.between(startJobEmployee, prevElementTimeSlot.getStartTime());
            if (startDuration != 0 && startDuration >= servicing.getTimeService()) {
                out.add(new TimeSlot(startJobEmployee, startDuration));
            }

            for (int i = 1; i < timeSlots.size(); i++) {
                LocalDateTime endTimePrevTimeSlot = prevElementTimeSlot.getEndTime();
                TimeSlot current = timeSlots.get(i);
                long currentDuration = ChronoUnit.MINUTES.between(endTimePrevTimeSlot, current.getStartTime());
                if (currentDuration != 0 && currentDuration >= servicing.getTimeService()) {
                    out.add(new TimeSlot(endTimePrevTimeSlot, currentDuration));
                }
                prevElementTimeSlot = current;
            }

            long endDuration = ChronoUnit.MINUTES.between(prevElementTimeSlot.getEndTime(), endJobEmployee);
            if (endDuration != 0 && endDuration >= servicing.getTimeService()) {
                out.add(new TimeSlot(prevElementTimeSlot.getEndTime(), endDuration));
            }
        } else {
            long duration = ChronoUnit.MINUTES.between(startJobEmployee, endJobEmployee);
            if (duration != 0 && duration >= servicing.getTimeService()) {
                out.add(new TimeSlot(startJobEmployee, duration));
            }
        }

        return out;
    }
}
