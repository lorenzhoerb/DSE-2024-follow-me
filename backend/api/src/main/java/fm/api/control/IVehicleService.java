package fm.api.control;

import fm.api.datafeeder.VehicleStatusDTO;

public interface IVehicleService {

    /**
     * Sends the status of a vehicle identified by its VIN (Vehicle Identification Number) to the appropriate RabbitMQ topic.
     * <p>
     * The status is sent to the RabbitMQ topic "data.vehicle.[vin]", where [vin] is the VIN provided in the parameters.
     * If the vehicle is currently in follow-me mode, the paired vehicle's VIN is provided in the status parameter; otherwise, it is null.
     * For following vehicles (FV), the target speed and lane are provided if in follow-me mode.
     *
     * @param vin    Vehicle Identification Number (VIN) of the vehicle to which the status is sent.
     * @param status Vehicle status containing information about whether the vehicle is currently in follow-me mode,
     *               and additional details such as target speed and lane for following vehicles (FV).
     */
    void sendStatus(String vin, VehicleStatusDTO status);
}
