package com.elvir.backend.model.mapper;

import com.elvir.backend.model.dto.EmployeeDto;
import com.elvir.backend.model.dto.ServicingDto;
import com.elvir.backend.model.entity.Employee;
import com.elvir.backend.model.entity.Servicing;
import com.elvir.backend.model.request.ServicingInfo;
import org.mapstruct.Mapper;

@Mapper
public interface ServicingMapper {
    ServicingDto servicingToServicingDto(Servicing servicing);
    Servicing servicingDtoToServicing(ServicingDto servicingDto);
    Servicing servicingInfoToServicing(ServicingInfo servicingInfo);
}
