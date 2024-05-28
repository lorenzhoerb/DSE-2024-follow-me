package fm.service.control.rest;

import fm.api.datafeeder.VehicleStatusDTO;
import fm.service.control.mongo.controller.MongoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:5173")
public class RestEndpoints {

    @Autowired
    MongoController controller;

    @GetMapping(value = "/control/status/{vin}")
    @ResponseBody
    public VehicleStatusDTO getStatus(@PathVariable("vin") String vin) {
        return controller.findByVin(vin);
    }

    @GetMapping(value = "/control/status")
    @ResponseBody
    public List<VehicleStatusDTO> getAll() {
        return controller.getStatusList();
    }

}
