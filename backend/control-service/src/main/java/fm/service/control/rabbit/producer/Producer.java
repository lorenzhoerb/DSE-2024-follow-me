package fm.service.control.rabbit.producer;

import fm.api.control.IVehicleService;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.service.control.rabbit.config.QueueCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Producer implements IVehicleService {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Value("${exchange.name}")
    private String exchange;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    QueueCreator creator;

    /**
     * Sends the status of a vehicle identified by its VIN (Vehicle Identification Number) to the appropriate RabbitMQ topic.
     * <p>
     * The status is sent to the RabbitMQ topic "data.vehicle.[vin]", where [vin] is the VIN provided in the parameters.
     * If the vehicle is currently in follow-me mode, the paired vehicle's VIN is provided in the status parameter; otherwise, it is null.
     * For following vehicles (FV), the target speed and lane are provided if in follow-me mode.
     *
     * @param vin    Vehicle Identification Number (VIN) of the vehicle to which the status is sent.
     * @param status Vehicle status containing information about whether the vehicle is currently in follow-me mode,
     *               and additional details such as target speed and lane for following vehicles (FV).
     */
    @Override
    public void sendStatus(String vin, VehicleStatusDTO status) {
        logger.info("sendStatus({}, {})", vin, status);
        //creator.createQueueBind(vin);
        rabbitTemplate.convertAndSend(exchange, "data.vehicle." + vin, status);
    }
}
