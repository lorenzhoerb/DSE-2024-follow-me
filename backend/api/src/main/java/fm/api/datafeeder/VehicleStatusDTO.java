package fm.api.datafeeder;

public class VehicleStatusDTO {
    private boolean isFollowMeModeActive;
    private String pairedVin;
    private TargetControlDTO targetControl;

    public VehicleStatusDTO(boolean isFollowMeModeActive) {
        this.isFollowMeModeActive = isFollowMeModeActive;
    }

    public VehicleStatusDTO(boolean isFollowMeModeActive, String pairedVin) {
        this.isFollowMeModeActive = isFollowMeModeActive;
        this.pairedVin = pairedVin;
    }

    public VehicleStatusDTO(boolean isFollowMeModeActive, String pairedVin, TargetControlDTO targetControl) {
        this.isFollowMeModeActive = isFollowMeModeActive;
        this.pairedVin = pairedVin;
        this.targetControl = targetControl;
    }

    public boolean isFollowMeModeActive() {
        return isFollowMeModeActive;
    }

    public void setFollowMeModeActive(boolean followMeModeActive) {
        isFollowMeModeActive = followMeModeActive;
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
