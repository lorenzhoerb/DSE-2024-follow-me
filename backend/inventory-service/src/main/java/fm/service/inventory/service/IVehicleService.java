package fm.service.inventory.service;

import fm.api.common.ConflictException;
import fm.api.common.EntityNotFoundException;
import fm.api.inventory.dto.VehicleDetailsDTO;
import fm.api.inventory.dto.VehicleRequestDTO;

import java.util.List;

public interface IVehicleService {

    /**
     * Creates a new vehicle with the provided details.
     *
     * @param vehicle The details of the vehicle to be created.
     * @return The details of the newly created vehicle.
     * @throws ConflictException if the specified model referenced in the vehicle request is not found.
     */
    VehicleDetailsDTO create(VehicleRequestDTO vehicle);

    /**
     * Retrieves the details of a vehicle by its VIN (Vehicle Identification Number).
     *
     * @param vin The VIN of the vehicle to be found.
     * @return The details of the vehicle identified by the provided VIN.
     * @throws EntityNotFoundException if no vehicle with the specified VIN is found.
     */
    VehicleDetailsDTO findByVin(String vin);

    /**
     * Retrieves details of all vehicles.
     *
     * @return A list containing details of all vehicles.
     */
    List<VehicleDetailsDTO> findAll();
}
