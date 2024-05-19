package fm.service.beachcomb.rest;

import fm.api.datafeeder.Location;
import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.VehicleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MatchingRest {
    @Autowired
    FindNearest nearest;

    @GetMapping(value = "/beachcomb/{vin}")
    @ResponseBody
    public List<VehicleDataDTO> matching(@PathVariable("vin") String vin, @RequestParam("type") VehicleType type) {
        List<VehicleDataDTO> result =new ArrayList<>();
        result.add(new VehicleDataDTO("33", new Location(), 12.0, 1,
                new TargetControlDTO(11.0, 2), LocalDateTime.now()));
        result.add(new VehicleDataDTO("44", new Location(), 12.0, 1,
                new TargetControlDTO(11.0, 2), LocalDateTime.now()));
        return result;
        //return nearest.findMatches(vin, type);
    }
}
