package fm.service.control.service;

import fm.api.common.EventMessageDTO;
import fm.api.common.LogLevel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EventMessageService {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.exchange.event}")
    private String exchange;

    public EventMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendEvent(EventMessageDTO event) {
        if (event.getLogLevel() == null) event.setLogLevel(LogLevel.DEBUG);
        rabbitTemplate.convertAndSend(exchange, "event.message.control." + event.getLogLevel(), event);
    }
}
