package fm.service.control.rabbit.consumer;

import fm.api.datafeeder.VehicleStatusDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomMessageListener {

    public void handleMessage(VehicleStatusDTO statusDTO) {
        System.out.println(statusDTO);
    }
}
