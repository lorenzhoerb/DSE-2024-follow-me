package dse.inventory.service;

import dse.inventory.config.RabbitMqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqTestService {
    private final RabbitMqProperties properties;
    private final RabbitTemplate rabbitTemplate;

    public RabbitMqTestService(RabbitMqProperties properties, RabbitTemplate rabbitTemplate) {
        this.properties = properties;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String message) {
        rabbitTemplate.convertAndSend(properties.getExchange(), properties.getRoutingKey(), message);
    }

    @RabbitListener(queues = "${dse.rabbitmq.queue}")
    public void receive(String message) {
        log.info("Received message: {}", message);
    }
}
