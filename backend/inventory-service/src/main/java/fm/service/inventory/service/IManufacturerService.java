package fm.service.inventory.service;

import fm.api.inventory.dto.ManufacturerDetailsDTO;
import fm.api.inventory.dto.ManufacturerRequestDTO;

import java.util.List;

public interface IManufacturerService {
    ManufacturerDetailsDTO create(ManufacturerRequestDTO manufacturer);

    List<ManufacturerDetailsDTO> findAll();

}
