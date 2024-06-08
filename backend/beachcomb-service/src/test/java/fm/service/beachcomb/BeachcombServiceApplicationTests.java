package fm.service.beachcomb;

import fm.api.datafeeder.Location;
import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.service.beachcomb.mongo.controller.MongoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BeachcombServiceApplicationTests {

    @Autowired
    MongoController controller;
    RestTemplate restTemplate = new RestTemplate();
    @Value("${beachcomb.rest.test}")
    private String url;

    @Test
    void repository_Test() {
        VehicleDataDTO dataDTO = new VehicleDataDTO("200", new Location(10.0, 15.0), 100.0,
                1, new TargetControlDTO(150.0, 2), LocalDateTime.now());
        controller.saveVehicle(dataDTO);
        dataDTO.setVelocity(200.0);
        dataDTO.setTargetControl(new TargetControlDTO(100.0, 1));
        controller.saveVehicle(dataDTO);
        dataDTO = controller.findVehicleByVin("200");
        assertThat(dataDTO != null);
        assertThat(dataDTO.getVelocity() == 200.0);
    }

    @Test
    void rest_test() {
        VehicleDataDTO dataDTO = new VehicleDataDTO("200", new Location(10.0, 15.0), 100.0,
                1, new TargetControlDTO(150.0, 2), LocalDateTime.now());
        controller.saveVehicle(dataDTO);
        VehicleDataDTO dataDTO1 = restTemplate.getForObject(uriBuilder("200"), VehicleDataDTO.class);
        assertThat(dataDTO1 != null);
        assertThat(dataDTO1.getTargetControl().getTargetVelocity() == 150.0);
    }

    private URI uriBuilder(String vin) {
        return UriComponentsBuilder.fromHttpUrl(url + "api/beachcomb/vehicles/{vin}")
                .buildAndExpand(vin)
                .toUri();
    }

}
