package fm.api.datafeeder;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Status")
@Data
public class VehicleStatusDTO {

    @Id
    private String vin;
    private boolean followMeModeActive;
    private String pairedVin;
    private TargetControlDTO targetControl;

    public VehicleStatusDTO() {
    }

    public VehicleStatusDTO(String vin, boolean followMeModeActive) {
        this.vin = vin;
        this.followMeModeActive = followMeModeActive;
    }

    public VehicleStatusDTO(String vin, boolean followMeModeActive, String pairedVin) {
        this.vin = vin;
        this.followMeModeActive = followMeModeActive;
        this.pairedVin = pairedVin;
    }

    public VehicleStatusDTO(String vin, boolean followMeModeActive, String pairedVin, TargetControlDTO targetControl) {
        this.vin = vin;
        this.followMeModeActive = followMeModeActive;
        this.pairedVin = pairedVin;
        this.targetControl = targetControl;
    }

    public boolean isFollowMeModeActive() {
        return followMeModeActive;
    }

    public void setFollowMeModeActive(boolean followMeModeActive) {
        this.followMeModeActive = followMeModeActive;
    }

    public String getPairedVin() {
        return pairedVin;
    }

    public void setPairedVin(String pairedVin) {
        this.pairedVin = pairedVin;
    }

    public TargetControlDTO getTargetControl() {
        return targetControl;
    }

    public void setTargetControl(TargetControlDTO targetControl) {
        this.targetControl = targetControl;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    @Override
    public String toString() {
        return "VehicleStatusDTO{" +
                "vin='" + vin + '\'' +
                ", followMeModeActive=" + followMeModeActive +
                ", pairedVin='" + pairedVin + '\'' +
                ", targetControl=" + targetControl +
                '}';
    }
}
