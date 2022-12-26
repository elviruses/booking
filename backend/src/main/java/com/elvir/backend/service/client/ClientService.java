package com.elvir.backend.service.client;

import com.elvir.backend.model.dto.ClientDto;
import com.elvir.backend.model.request.ClientInfo;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    void create(ClientInfo clientInfo);

    void update(UUID uuid, ClientInfo clientInfo);

    void delete(UUID uuid);

    ClientDto get(UUID uuid);

    List<ClientDto> getList();

    void sendCode(Long phone);
}
