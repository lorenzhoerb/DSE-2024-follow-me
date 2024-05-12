package fm.datafeeder;

import fm.api.datafeeder.VehicleDataDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DataSenderService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbit.exchange.vehicle}")
    private String exchange;

    public DataSenderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

   public void sendData(VehicleDataDTO vehicleDataDTO) {
        rabbitTemplate.convertAndSend(exchange, "data.beachcomb", vehicleDataDTO);
   }
}
