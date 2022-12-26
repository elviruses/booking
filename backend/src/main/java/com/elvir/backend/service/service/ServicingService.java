package com.elvir.backend.service.service;

import com.elvir.backend.model.dto.ServicingDto;
import com.elvir.backend.model.request.ServicingInfo;

import java.util.List;
import java.util.UUID;

public interface ServicingService {

    void create(ServicingInfo servicingInfo);

    void update(UUID uuid, ServicingInfo servicingInfo);

    void delete(UUID uuid);

    ServicingDto get(UUID uuid);

    List<ServicingDto> getList();
}
