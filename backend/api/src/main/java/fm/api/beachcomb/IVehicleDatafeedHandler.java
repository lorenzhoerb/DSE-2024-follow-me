package fm.api.beachcomb;

import fm.api.datafeeder.VehicleDataDTO;

/**
 * Represents a handler for processing real-time data of vehicles.
 */
public interface IVehicleDatafeedHandler {

    /**
     * Handles real-time vehicle data received via RabbitMQ under the topic "data.beachcomb".
     *
     * @param vehicleData The data of the vehicle to be processed, typically containing various parameters and metrics.
     *                    For Leading Vehicles (LV), the target control data is provided.
     *                    For Following Vehicles (FV), the target control is null.
     *                    All other values are assumed to be provided.
     */
    void handleVehicleData(VehicleDataDTO vehicleData);
}
