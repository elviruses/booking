package com.elvir.backend.service.mq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQSender {

    private final AmqpTemplate rabbitTemplate;

    @Value("${rabbitmq.queue}")
    String queueName;

    public void send(Object obj) {
        rabbitTemplate.convertAndSend(queueName, obj);
    }
}
