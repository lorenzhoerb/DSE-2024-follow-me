package fm.service.inventory.service.impl;

import fm.api.common.ConflictException;
import fm.api.inventory.dto.ManufacturerDetailsDTO;
import fm.api.inventory.dto.ManufacturerRequestDTO;
import fm.service.inventory.model.Manufacturer;
import fm.service.inventory.repository.ManufacturerRepository;
import fm.service.inventory.service.IManufacturerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ManufacturerService implements IManufacturerService {
    private final static Logger LOGGER = LoggerFactory.getLogger(ManufacturerService.class);
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerService(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public ManufacturerDetailsDTO create(ManufacturerRequestDTO manufacturer) {
        LOGGER.info("createManufacturer({})", manufacturer);
        manufacturerRepository.findByManufacturerCode(manufacturer.getManufacturerCode())
                .ifPresent(existingManufacturer -> {
                    throw new ConflictException("Manufacturer with code '" + manufacturer.getManufacturerCode() +
                            "' already exists.");
                });

        Manufacturer m = Manufacturer.builder()
                .name(manufacturer.getName().trim())
                .manufacturerCode(manufacturer.getManufacturerCode().trim().toUpperCase())
                .build();

        manufacturerRepository.save(m);

        return ManufacturerDetailsDTO.builder()
                .id(m.getId())
                .name(m.getName())
                .manufacturerCode(m.getManufacturerCode())
                .build();
    }

    @Override
    public List<ManufacturerDetailsDTO> findAll() {
        LOGGER.info("findAllManufacturers()");
        return manufacturerRepository.findAll()
                .stream()
                .map(m -> ManufacturerDetailsDTO.builder()
                        .id(m.getId())
                        .name(m.getName())
                        .manufacturerCode(m.getManufacturerCode())
                        .build())
                .toList();
    }
}
