package com.elvir.backend.service.client;

import com.elvir.backend.model.dto.ClientDto;
import com.elvir.backend.model.entity.Client;
import com.elvir.backend.model.repo.ClientRepository;
import com.elvir.backend.service.mq.RabbitMQSender;
import com.elvir.backend.model.mapper.ClientMapper;
import com.elvir.backend.model.request.ClientInfo;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final RabbitMQSender rabbitSender;
    private final ClientRepository clientRepository;
    private final ClientMapper MAPPER = Mappers.getMapper(ClientMapper.class);

    @Override
    @Transactional
    public void create(ClientInfo clientInfo) {
        Client client = buildClient(clientInfo);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void update(UUID uuid, ClientInfo clientInfo) {
        Client client = clientRepository.findById(uuid).orElseThrow(RuntimeException::new);
        clientRepository.save(filledClient(client, clientInfo));
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        boolean existsClient = clientRepository.existsById(uuid);
        if (existsClient) {
            clientRepository.deleteById(uuid);
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDto get(UUID uuid) {
        Client client = clientRepository.findById(uuid).orElseThrow(RuntimeException::new);
        return MAPPER.clientToClientDto(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDto> getList() {
        return clientRepository.findAll().stream()
                .map(MAPPER::clientToClientDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public void sendCode(Long phone) {
        rabbitSender.send(phone.toString());
    }

    private Client buildClient(ClientInfo clientInfo) {
        return Client.builder()
                .firstName(clientInfo.getFirstName())
                .lastName(clientInfo.getLastName())
                .phone(clientInfo.getPhone())
                .build();
    }

    private Client filledClient(Client client, ClientInfo clientInfo) {
        client.setFirstName(clientInfo.getFirstName());
        client.setLastName(clientInfo.getLastName());
        client.setPhone(clientInfo.getPhone());
        return client;
    }
}
