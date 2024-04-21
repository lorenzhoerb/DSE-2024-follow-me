package fm.api.inventory;

public class VehicleDetailsDTO {
    private String vin;
    private VehicleType vehicleType;
    private String model;
    private String manufacturer;
    private String manufacturerCode;

    public VehicleDetailsDTO() {
    }


    public VehicleDetailsDTO(String vin, VehicleType vehicleType, String model, String manufacturer, String manufacturerCode) {
        this.vin = vin;
        this.vehicleType = vehicleType;
        this.model = model;
        this.manufacturer = manufacturer;
        this.manufacturerCode = manufacturerCode;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufacturerCode() {
        return manufacturerCode;
    }

    public void setManufacturerCode(String manufacturerCode) {
        this.manufacturerCode = manufacturerCode;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
}
