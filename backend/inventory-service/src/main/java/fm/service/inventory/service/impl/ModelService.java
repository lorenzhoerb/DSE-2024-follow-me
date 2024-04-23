package fm.service.inventory.service.impl;

import fm.api.common.ConflictException;
import fm.api.inventory.dto.ModelDetailsDTO;
import fm.api.inventory.dto.ModelRequestDTO;
import fm.service.inventory.model.Manufacturer;
import fm.service.inventory.model.Model;
import fm.service.inventory.repository.ManufacturerRepository;
import fm.service.inventory.repository.ModelRepository;
import fm.service.inventory.service.IModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ModelService implements IModelService {
    public static final long VEHICLE_COUNT_START = 1000L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ModelService.class);
    private final ManufacturerRepository manufacturerRepository;
    private final ModelRepository modelRepository;

    public ModelService(ManufacturerRepository manufacturerRepository, ModelRepository modelRepository) {
        this.manufacturerRepository = manufacturerRepository;
        this.modelRepository = modelRepository;
    }

    @Override
    public ModelDetailsDTO create(ModelRequestDTO model) {
        LOGGER.info("createModel({})", model);
        Manufacturer manufacturer = manufacturerRepository.findById(model.getManufacturerId())
                .orElseThrow(() -> new ConflictException("Manufacturer with id: '" + model.getManufacturerId() + "' does not exist."));

        Model m = Model.builder()
                .name(model.getName())
                .manufacturer(manufacturer)
                .vehicleCount(VEHICLE_COUNT_START)
                .build();

        modelRepository.save(m);

        return ModelDetailsDTO.builder()
                .id(m.getId())
                .name(model.getName().trim())
                .manufacturerId(model.getManufacturerId())
                .build();
    }
}
