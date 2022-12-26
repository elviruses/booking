package com.elvir.handlers;

import com.elvir.entity.Client;
import com.elvir.repo.ClientRepository;
import com.elvir.util.BotConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class ChannelHandlers extends TelegramLongPollingBot {

    private final ObjectMapper mapper = new ObjectMapper();
    private final ClientRepository clientRepository;

    @Override
    public String getBotUsername() {
        return BotConfig.NAME;
    }

    @Override
    public String getBotToken() {
        return BotConfig.TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText(update.getMessage().getText());

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = "test-queue")
    @Transactional
    public void listen(SendVerifyCode sendVerifyCode) {
        System.out.println("Message read from myQueue : " + sendVerifyCode);
        Client client = clientRepository.findById(sendVerifyCode.getUuidClient()).orElseThrow(RuntimeException::new);
        String random = String.valueOf((int) (Math.random() * 10000));
        SendMessage message = new SendMessage();
        message.setChatId(sendVerifyCode.getChatId());
        message.setText(String.format("Ваш проверочный код: %s", random));
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        client.setSentCode(random);
        clientRepository.save(client);
    }
}
