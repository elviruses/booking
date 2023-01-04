package com.elvir.backend.service.client;

import com.elvir.backend.model.dto.ClientDto;
import com.elvir.backend.model.entity.Client;
import com.elvir.backend.model.repo.ClientRepository;
import com.elvir.backend.service.mq.RabbitMQSender;
import com.elvir.backend.model.mapper.ClientMapper;
import com.elvir.backend.model.request.ClientInfo;
import com.elvir.backend.utils.RandomUtil;
import com.elvir.library.mq.TelegramMessage;
import com.elvir.library.mq.WhatsAppMessage;
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
        final Client client = MAPPER.ClientInfoToClient(clientInfo);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void update(UUID uuid, ClientInfo clientInfo) {
        final Client client = clientRepository.findById(uuid).orElseThrow(RuntimeException::new);
        client.updateByClientInfo(clientInfo);
        clientRepository.save(client);
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
        final Client client = clientRepository.findById(uuid).orElseThrow(RuntimeException::new);
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
    @Transactional
    public void sendVerifyCode(UUID uuid) {
        String randomCode = RandomUtil.generateRandomVerifyCode();
        String message = String.format("Ваш проверочный код: %s", randomCode);
        final Client client = clientRepository.findById(uuid).orElseThrow(RuntimeException::new);
        if (client.getChatId() != null) {
            TelegramMessage telegramMessage = TelegramMessage.builder()
                    .chatId(client.getChatId())
                    .message(message)
                    .build();
            rabbitSender.sendToTelegram(telegramMessage);
        } else {
            WhatsAppMessage whatsAppMessage = WhatsAppMessage.builder()
                    .phone(client.getPhone().toString())
                    .message(message)
                    .build();
            rabbitSender.sendToWhatsApp(whatsAppMessage);
        }
        client.setSentCode(randomCode);
        clientRepository.save(client);
    }
}
