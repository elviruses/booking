package com.elvir.backend.service.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.telegram}")
    String telegramQueueName;

    @Value("${rabbitmq.whatsapp}")
    String whatsAppQueueName;

    public void sendToTelegram(Object obj) {
        rabbitTemplate.convertAndSend(telegramQueueName, obj);
    }

    public void sendToWhatsApp(Object obj) {
        rabbitTemplate.convertAndSend(whatsAppQueueName, obj);
    }
}
