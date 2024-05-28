package fm.service.inventory.service;

import fm.api.common.ConflictException;
import fm.api.inventory.dto.ModelDetailsDTO;
import fm.api.inventory.dto.ModelRequestDTO;

public interface IModelService {
    /**
     * Creates a new model with the provided details.
     *
     * @param model The details of the model to be created.
     * @return The details of the newly created model.
     * @throws ConflictException if the manufacturer referenced in the model request does not exist.
     */
    ModelDetailsDTO create(ModelRequestDTO model);
}
