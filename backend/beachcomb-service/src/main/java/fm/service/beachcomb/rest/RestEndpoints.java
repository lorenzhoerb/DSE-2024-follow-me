package fm.service.beachcomb.rest;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.VehicleType;
import fm.service.beachcomb.mongo.controller.MongoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestEndpoints {
    @Autowired
    FindNearest nearest;
    @Autowired
    MongoController controller;

    @GetMapping(value = "/beachcomb/{vin}")
    @ResponseBody
    public List<VehicleDataDTO> matching(@PathVariable("vin") String vin, @RequestParam("type") VehicleType type) {
        return nearest.findMatches(vin, type);
    }

    @GetMapping(value = "/beachcomb/vehicles/{vin}")
    public VehicleDataDTO getVehicle(@PathVariable("vin") String vin) {
        return controller.findVehicleByVin(vin);
    }

    @GetMapping(value = "/beachcomb/vehicles")
    public List<VehicleDataDTO> getAll() {
        return controller.getVehicles();
    }
}
