package com.elvir.backend.model.mapper;

import com.elvir.backend.model.dto.EmployeeDto;
import com.elvir.backend.model.entity.Employee;
import com.elvir.backend.model.request.EmployeeInfo;
import org.mapstruct.Mapper;

@Mapper
public interface EmployeeMapper {
    EmployeeDto employeeToEmployeeDto(Employee employee);
    Employee employeeDtoToEmployee(EmployeeDto employeeDto);
    Employee employeeInfoToEmployee(EmployeeInfo employeeInfo);
}
