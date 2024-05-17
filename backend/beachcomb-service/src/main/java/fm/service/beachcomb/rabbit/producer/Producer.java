package fm.service.beachcomb.rabbit.producer;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.dto.VehicleBaseDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value("${exchange.name}")
    private String exchange;
    @Value("${fromInventory.key.name}")
    private String fromInventoryKey;
    @Value("${fromDatafeeder.key.name}")
    private String fromDatafeederKey;

    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMessage(VehicleDataDTO vehicle) {
        rabbitTemplate.convertAndSend(exchange, fromDatafeederKey, vehicle);
    }

    public void sendMessage(VehicleBaseDTO base) {
        rabbitTemplate.convertAndSend(exchange, fromInventoryKey, base);
    }
}
