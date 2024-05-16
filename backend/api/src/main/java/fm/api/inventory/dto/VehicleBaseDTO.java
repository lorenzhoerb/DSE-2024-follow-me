package fm.api.inventory.dto;

import fm.api.inventory.VehicleType;
import lombok.Data;

import java.io.Serializable;

@Data
public class VehicleBaseDTO implements Serializable {
    private String vin;
    private VehicleType vehicleType;

    public VehicleBaseDTO(String vin, VehicleType vehicleType) {
        this.vin = vin;
        this.vehicleType = vehicleType;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "VehicleBaseDTO{" +
                "vin='" + vin + '\'' +
                ", vehicleType=" + vehicleType +
                '}';
    }
}
