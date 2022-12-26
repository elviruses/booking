package com.elvir.backend.controller;

import com.elvir.backend.model.dto.EmployeeDto;
import com.elvir.backend.model.request.EmployeeInfo;
import com.elvir.backend.service.employee.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody EmployeeInfo employeeInfo) {
        System.out.println(employeeInfo);
        employeeService.create(employeeInfo);
    }

    @PatchMapping("/employee/{id}")
    public void update(@PathVariable("id") UUID uuid, @RequestBody EmployeeInfo employeeInfo) {
        employeeService.update(uuid, employeeInfo);
    }

    @DeleteMapping("/employee/{id}")
    public void delete(@PathVariable("id") UUID uuid) {
        employeeService.delete(uuid);
    }

    @GetMapping("/employee/{id}")
    public EmployeeDto get(@PathVariable("id") UUID uuid) {
        return employeeService.get(uuid);
    }

    @GetMapping("/employee/list")
    public List<EmployeeDto> getList() {
        return employeeService.getList();
    }

    @PatchMapping("/employee/add/skill/{id}")
    public void addSkill(@PathVariable("id") UUID uuid, @RequestBody List<UUID> servicingUUIDs) {
        employeeService.addSkill(uuid, servicingUUIDs);
    }
}
