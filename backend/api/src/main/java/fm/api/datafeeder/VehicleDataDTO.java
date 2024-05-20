package fm.api.datafeeder;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

// GET beachcomb/vehicles
// GET beachcomb/vehicles/[id]
@Document(collection = "Vehicle")
@Data
public class VehicleDataDTO {

    @Id
    private String vin;
    private Location location;
    private Double velocity;
    private Integer lane;
    private TargetControlDTO targetControl;
    private LocalDateTime timestamp;

    public VehicleDataDTO(String vin, Location location, Double velocity, Integer lane, TargetControlDTO targetControl, LocalDateTime timestamp) {
        this.vin = vin;
        this.location = location;
        this.velocity = velocity;
        this.lane = lane;
        this.targetControl = targetControl;
        this.timestamp = timestamp;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Double getVelocity() {
        return velocity;
    }

    public void setVelocity(Double velocity) {
        this.velocity = velocity;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public TargetControlDTO getTargetControl() {
        return targetControl;
    }

    public void setTargetControl(TargetControlDTO targetControl) {
        this.targetControl = targetControl;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
