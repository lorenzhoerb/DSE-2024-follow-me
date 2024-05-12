package fm.api.datafeeder;

public class VehicleStatusDTO {
    private String vin;
    private Boolean followMeModeActive;
    private String pairedVin;
    private TargetControlDTO targetControl;

    public VehicleStatusDTO() {
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Boolean getFollowMeModeActive() {
        return followMeModeActive;
    }

    public void setFollowMeModeActive(Boolean followMeModeActive) {
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
}
