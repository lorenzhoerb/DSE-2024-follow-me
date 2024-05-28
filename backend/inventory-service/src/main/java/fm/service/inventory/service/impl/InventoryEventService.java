package fm.service.inventory.service.impl;

import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.inventory.config.InventoryEventProperties;
import fm.service.inventory.service.IInventoryEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventService implements IInventoryEventService {

    private final RabbitTemplate rabbitTemplate;
    private final InventoryEventProperties inventoryEventProps;
    private final static Logger LOGGER = LoggerFactory.getLogger(InventoryEventService.class);

    public InventoryEventService(RabbitTemplate rabbitTemplate, InventoryEventProperties inventoryEventProps) {
        this.rabbitTemplate = rabbitTemplate;
        this.inventoryEventProps = inventoryEventProps;
    }

    @Override
    public void sendVehicleCreatedEvent(VehicleBaseDTO vehicle) {
        LOGGER.info("sendVehicleCreatedEvent({})", vehicle);
        rabbitTemplate.convertAndSend(inventoryEventProps.getExchange(), inventoryEventProps.getVehicleCreatedTopic(), vehicle);
        LOGGER.debug("sent vehicleCreatedEvent({})", vehicle);
    }
}
