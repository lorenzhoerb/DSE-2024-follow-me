package fm.api.datafeeder;

public class TargetControlDTO {
    private Double targetVelocity;
    private Integer targetLane;

    public TargetControlDTO(Double targetVelocity, Integer targetLane) {
        this.targetVelocity = targetVelocity;
        this.targetLane = targetLane;
    }

    public Double getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(Double targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public Integer getTargetLane() {
        return targetLane;
    }

    public void setTargetLane(Integer targetLane) {
        this.targetLane = targetLane;
    }
}
