package com.elvir.backend.model.mapper;

import com.elvir.backend.model.dto.ClientDto;
import com.elvir.backend.model.entity.Client;
import com.elvir.backend.model.request.ClientInfo;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientDto clientToClientDto(Client client);
    Client clientDtoToClient(ClientDto clientDto);
    Client ClientInfoToClient(ClientInfo clientInfo);
}
