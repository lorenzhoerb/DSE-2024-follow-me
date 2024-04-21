package fm.api.inventory;

public class VehicleBaseDTO {
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
}
