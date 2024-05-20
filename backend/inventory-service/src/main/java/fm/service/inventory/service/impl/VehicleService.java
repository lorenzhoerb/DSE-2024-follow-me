package fm.service.inventory.service.impl;

import fm.api.common.ConflictException;
import fm.api.common.EntityNotFoundException;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.api.inventory.dto.VehicleDetailsDTO;
import fm.api.inventory.dto.VehicleRequestDTO;
import fm.service.inventory.model.Manufacturer;
import fm.service.inventory.model.Model;
import fm.service.inventory.model.Vehicle;
import fm.service.inventory.repository.ModelRepository;
import fm.service.inventory.repository.VehicleRepository;
import fm.service.inventory.service.IVehicleService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class VehicleService implements IVehicleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(Vehicle.class);
    private final ModelRepository modelRepository;
    private final VehicleRepository vehicleRepository;
    private final InventoryEventService inventoryEventService;
    private final Lock vehicleIdLock = new ReentrantLock();

    public VehicleService(ModelRepository modelRepository, VehicleRepository vehicleRepository, InventoryEventService inventoryEventService) {
        this.modelRepository = modelRepository;
        this.vehicleRepository = vehicleRepository;
        this.inventoryEventService = inventoryEventService;
    }

    @Transactional
    @Override
    public VehicleDetailsDTO create(VehicleRequestDTO vehicle) {
        LOGGER.info("createVehicle({})", vehicle);
        vehicleIdLock.lock();
        Model model = modelRepository.findById(vehicle.getModelId())
                .orElseThrow(() -> new ConflictException("Model with id: '" + vehicle.getModelId() + "' not found."));

        String vin = generateVin(model);
        model.setVehicleCount(model.getVehicleCount() + 1);
        model = modelRepository.save(model);
        vehicleIdLock.unlock();

        Vehicle vehicleEntity = Vehicle.builder()
                .vin(vin)
                .type(vehicle.getType())
                .model(model)
                .build();

        vehicleEntity = vehicleRepository.save(vehicleEntity);

        inventoryEventService.sendVehicleCreatedEvent(
                new VehicleBaseDTO(vehicleEntity.getVin(), vehicleEntity.getType())
        );

        return mapVehicleToVehicleDetailsDto(vehicleEntity);
    }

    @Override
    public VehicleDetailsDTO findByVin(String vin) {
        LOGGER.info("findByVin({})", vin);
        Vehicle vehicle = vehicleRepository.findVehicleByVin(vin)
                .orElseThrow(() -> new EntityNotFoundException("Vehicle with vin: '" + vin + "' not found."));
        return mapVehicleToVehicleDetailsDto(vehicle);
    }

    @Override
    public List<VehicleDetailsDTO> findAll() {
        LOGGER.info("findAll()");
        return vehicleRepository.findAll()
                .stream()
                .map(this::mapVehicleToVehicleDetailsDto)
                .toList();
    }

    private VehicleDetailsDTO mapVehicleToVehicleDetailsDto(Vehicle vehicle) {
        return VehicleDetailsDTO.builder()
                .vin(vehicle.getVin())
                .type(vehicle.getType())
                .modelId(vehicle.getModel().getId())
                .manufacturer(vehicle.getModel().getManufacturer().getName())
                .manufacturerCode(vehicle.getModel().getManufacturer().getManufacturerCode())
                .model(vehicle.getModel().getName())
                .build();
    }

    private String generateVin(Model model) {
        Long vehicleId = model.getVehicleCount();
        Manufacturer manufacturer = model.getManufacturer();
        return String.format("%s-%s-%d", manufacturer.getManufacturerCode(), model.getName(), vehicleId);
    }
}
