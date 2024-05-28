package fm.service.beachcomb.rest;

import fm.api.datafeeder.Location;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.VehicleType;
import fm.service.beachcomb.mongo.controller.MongoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindNearest {
    @Autowired
    MongoController controller;
    private final double EARTH_RADIUS = 6371;
    @Value("${range.to.match}")
    private double matchingRange;

    public List<VehicleDataDTO> findMatches(String vin, VehicleType type) {
        List<String> vehicles;
        if (type == VehicleType.LV) vehicles = controller.getVehiclesByType(VehicleType.FV);
        else vehicles = controller.getVehiclesByType(VehicleType.LV);
        Location loc1 = controller.findVehicleByVin(vin).getLocation();
        List<VehicleDataDTO> results = new ArrayList<>();
        for (String v : vehicles) {
            VehicleDataDTO vehicle = controller.findVehicleByVin(v);
            Location loc2 = vehicle.getLocation();
            if (calculateDistance(loc1.getLatitude(), loc1.getLongitude(), loc2.getLatitude(), loc2.getLongitude())
                    < matchingRange)
                results.add(vehicle);
        }
        return results;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lat2Rad = Math.toRadians(lat2);
        double lon1Rad = Math.toRadians(lon1);
        double lon2Rad = Math.toRadians(lon2);

        double x = (lon2Rad - lon1Rad) * Math.cos((lat1Rad + lat2Rad) / 2);
        double y = (lat2Rad - lat1Rad);
        double distance = Math.sqrt(x * x + y * y) * EARTH_RADIUS;

        return distance;
    }
}
