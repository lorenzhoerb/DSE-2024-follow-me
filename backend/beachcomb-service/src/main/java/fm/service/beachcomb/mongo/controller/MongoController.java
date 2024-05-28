package fm.service.beachcomb.mongo.controller;

import fm.api.datafeeder.VehicleDataDTO;
import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.beachcomb.mongo.repository.VehicleBaseDTORepository;
import fm.service.beachcomb.mongo.repository.VehicleDataDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoController {

    @Autowired
    private VehicleDataDTORepository dataDTORepository;
    @Autowired
    private VehicleBaseDTORepository baseDTORepository;

    public List<VehicleDataDTO> getVehicles() {
        return dataDTORepository.findAll();
    }

    public void saveVehicle(VehicleDataDTO vehicle) {
        dataDTORepository.save(vehicle);
    }

    public VehicleDataDTO findVehicleByVin(String vin) {
        Optional<VehicleDataDTO> vehicle = dataDTORepository.findById(vin);
        if (vehicle.isPresent()) return vehicle.get();
        return null;
    }

    public List<VehicleBaseDTO> getBaseList() {
        return baseDTORepository.findAll();
    }

    public void saveBase(VehicleBaseDTO base) {
        baseDTORepository.save(base);
    }

    public List<String> getVehiclesByType(VehicleType type) {
        List<VehicleBaseDTO> baseDTOs = getBaseList();
        List<String> vins = new ArrayList<>();
        if (!baseDTOs.isEmpty()) {
            for (VehicleBaseDTO vehicle : baseDTOs) {
                if (vehicle.getVehicleType() == type) vins.add(vehicle.getVin());
            }
        }
        return vins;
    }

    public void freshStart() {
        dataDTORepository.deleteAll();
        baseDTORepository.deleteAll();
    }
}
