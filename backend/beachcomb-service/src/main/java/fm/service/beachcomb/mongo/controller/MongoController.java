package fm.service.beachcomb.mongo.controller;

import fm.api.datafeeder.VehicleDataDTO;
import fm.service.beachcomb.mongo.repository.VehicleDataDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoController {

    @Autowired
    private VehicleDataDTORepository repository;

    public List<VehicleDataDTO> getVehicles() {
        return repository.findAll();
    }

    public void saveVehicle(VehicleDataDTO vehicle) {
        repository.save(vehicle);
    }

    public VehicleDataDTO findByVin(String vin) {
        Optional<VehicleDataDTO> vehicle = repository.findById(vin);
        if (vehicle.isPresent()) return vehicle.get();
        return null;
    }

}
