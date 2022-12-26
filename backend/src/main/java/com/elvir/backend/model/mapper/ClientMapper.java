package com.elvir.backend.model.mapper;

import com.elvir.backend.model.entity.Client;
import com.elvir.backend.model.dto.ClientDto;
import com.elvir.backend.model.task.SendVerifyCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {
    ClientDto clientToClientDto(Client client);
    Client clientDtoToClient(ClientDto clientDto);

    @Mapping(target="uuidClient", source="client.id")
    SendVerifyCode clientToSendVerifyCode(Client client);
}
