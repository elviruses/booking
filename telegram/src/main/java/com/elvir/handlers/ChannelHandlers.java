package com.elvir.handlers;

import com.elvir.library.mq.TelegramMessage;
import com.elvir.utils.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class ChannelHandlers extends TelegramLongPollingBot {

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

    @RabbitListener(queues = "${rabbitmq.telegram}")
    public void listen(TelegramMessage telegramMessage) {
        System.out.println("Message read from myQueue : " + telegramMessage);

        SendMessage message = new SendMessage();
        message.setChatId(telegramMessage.getChatId());
        message.setText(telegramMessage.getMessage());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
