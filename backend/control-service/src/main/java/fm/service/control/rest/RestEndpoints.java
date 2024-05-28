package fm.service.control.rest;

import fm.api.datafeeder.VehicleStatusDTO;
import fm.service.control.mongo.controller.MongoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestEndpoints {

    @Autowired
    MongoController controller;

    @GetMapping(value = "status/{vin}")
    @ResponseBody
    public VehicleStatusDTO getStatus(@PathVariable("vin") String vin) {
        return controller.findByVin(vin);
    }

    @GetMapping(value = "status")
    @ResponseBody
    public List<VehicleStatusDTO> getAll() {
        return controller.getStatusList();
    }

}
