package fm.service.control;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.service.control.mongo.controller.MongoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ControlServiceApplicationTests {

    @Autowired
    MongoController controller;
    @Autowired
    RestTemplate restTemplate;
    @Value("${control.rest.test}")
    private String url;

    @Test
    void repository_Test() {
        VehicleStatusDTO statusDTO = new VehicleStatusDTO("100", true, "99",
                new TargetControlDTO(50.0, 2));
        controller.saveStatus(statusDTO);
        statusDTO.setPairedVin("800");
        statusDTO.setTargetControl(new TargetControlDTO(100.0, 1));
        controller.saveStatus(statusDTO);
        statusDTO = controller.findByVin("100");
        assertThat(statusDTO != null);
        assertThat(statusDTO.getPairedVin().equals("800"));
    }

    @Test
    void rest_test(){
        VehicleStatusDTO statusDTO = new VehicleStatusDTO("100", true, "99",
                new TargetControlDTO(50.0, 2));
        controller.saveStatus(statusDTO);
        VehicleStatusDTO statusDTO1 = restTemplate.getForObject(uriBuilder("100"),VehicleStatusDTO.class);
        assertThat(statusDTO1 != null);
        assertThat(statusDTO1.getPairedVin().equals("99"));
    }

    private URI uriBuilder(String vin) {
        return UriComponentsBuilder.fromHttpUrl(url + "api/control/status/{vin}")
                .buildAndExpand(vin)
                .toUri();
    }

}
