package com.elvir.backend.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EmployeeInfo {

    String firstName;

    String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    LocalDate birthday;

    LocalTime startTime;

    LocalTime endTime;

    LocalTime lunchStart;

    LocalTime lunchEnd;

    Integer workDays;

    Integer offDays;
}
