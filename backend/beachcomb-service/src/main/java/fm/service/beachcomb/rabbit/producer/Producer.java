package fm.service.beachcomb.rabbit.producer;

import fm.api.datafeeder.VehicleDataDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value("${exchange.name}")
    private String exchange;
    @Value("${key.name}")
    private String key;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(VehicleDataDTO vehicle) {
        rabbitTemplate.convertAndSend(exchange, key, vehicle);
    }
}
