package fm.api.inventory;

import fm.api.inventory.dto.VehicleBaseDTO;

/**
 * Represents a handler for events related to vehicles, dispatched via RabbitMQ under the topic "entity.vehicle".
 */
public interface IVehicleEventHandler {
    /**
     * Handles the creation of a vehicle. Upon the creation of a vehicle, this method will publish an event
     * under the topic "entity.vehicle.created".
     *
     * @param vehicleData Information about the newly created vehicle,
     *                    including its VIN (Vehicle Identification Number) and type (FV or LV).
     */
    void handleVehicleCreated(VehicleBaseDTO vehicleData);
}
