package fm.service.inventory.service;

import fm.api.inventory.dto.VehicleBaseDTO;

public interface IInventoryEventService {
    void sendVehicleCreatedEvent(VehicleBaseDTO vehicle);
}
