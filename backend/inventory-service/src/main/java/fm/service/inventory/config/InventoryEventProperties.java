package fm.service.inventory.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class InventoryEventProperties {
    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.event.vehicle.created}")
    private String vehicleCreatedTopic;
}
