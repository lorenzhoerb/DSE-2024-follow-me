package fm.service.control.mongo.controller;

import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.mongo.repository.VehicleBaseDTORepository;
import fm.service.control.mongo.repository.VehicleStatusDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MongoController {

    @Autowired
    private VehicleStatusDTORepository statusRepo;
    @Autowired
    private VehicleBaseDTORepository baseRepo;

    public List<VehicleStatusDTO> getStatusList() {
        return statusRepo.findAll();
    }

    public void saveStatus(VehicleStatusDTO status) {
        statusRepo.save(status);
    }

    public VehicleStatusDTO findByVin(String vin) {
        Optional<VehicleStatusDTO> status = statusRepo.findById(vin);
        if (status.isPresent()) return status.get();
        return null;
    }

    public List<VehicleBaseDTO> getBaseList() {
        return baseRepo.findAll();
    }

    public void saveBase(VehicleBaseDTO base) {
        baseRepo.save(base);
    }

    public VehicleBaseDTO findBaseByVin(String vin) {
        Optional<VehicleBaseDTO> base = baseRepo.findById(vin);
        if (base.isPresent()) return base.get();
        return null;
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

    public List<String> getAllVins() {
        List<VehicleBaseDTO> baseDTOs = getBaseList();
        List<String> vins = new ArrayList<>();
        if (!baseDTOs.isEmpty()) {
            for (VehicleBaseDTO vehicle : baseDTOs) {
                vins.add(vehicle.getVin());
            }
        }
        return vins;
    }

}
