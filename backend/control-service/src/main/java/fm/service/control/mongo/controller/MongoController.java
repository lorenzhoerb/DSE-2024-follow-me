package fm.service.control.mongo.controller;

import fm.api.datafeeder.VehicleStatusDTO;
import fm.service.control.mongo.repository.VehicleStatusDTORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MongoController {

    @Autowired
    private VehicleStatusDTORepository repository;

    public List<VehicleStatusDTO> getStatusList() {
        return repository.findAll();
    }

    public void saveStatus(VehicleStatusDTO status) {
        repository.save(status);
    }

    public VehicleStatusDTO findByVin(String vin) {
        Optional<VehicleStatusDTO> status = repository.findById(vin);
        if (status.isPresent()) return status.get();
        return null;
    }

}
