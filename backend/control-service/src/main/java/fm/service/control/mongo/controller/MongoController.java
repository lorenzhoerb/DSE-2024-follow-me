package fm.service.control.mongo.controller;

import fm.api.datafeeder.VehicleStatusDTO;
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

    public List<VehicleStatusDTO> getPaired() {
        List<VehicleStatusDTO> vehicles = statusRepo.findAll();
        List<VehicleStatusDTO> vehiclesCopy = new ArrayList<>(vehicles);
        for (VehicleStatusDTO v : vehicles) {
            if (v.getPairedVin() == null) vehiclesCopy.remove(v);
        }
        return vehiclesCopy;
    }

    public List<VehicleStatusDTO> getNotPaired() {
        List<VehicleStatusDTO> vehicles = statusRepo.findAll();
        List<VehicleStatusDTO> vehiclesCopy = new ArrayList<>(vehicles);
        for (VehicleStatusDTO v : vehicles) {
            if (v.getPairedVin() != null) vehiclesCopy.remove(v);
        }
        return vehiclesCopy;
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

    public void freshStart() {
        baseRepo.deleteAll();
        statusRepo.deleteAll();
    }
}
