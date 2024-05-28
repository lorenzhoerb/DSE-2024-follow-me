package fm.datafeeder;

import fm.api.datafeeder.Location;

public class Vehicle {
    boolean isLeadingVehicle;

    private String vin;
    private double velocity;
    private int lane;
    private double targetVelocity;
    private int targetLane;
    private boolean followMeMode;
    private Location location;

    public boolean isLeadingVehicle() {
        return isLeadingVehicle;
    }

    public void setLeadingVehicle(boolean leadingVehicle) {
        isLeadingVehicle = leadingVehicle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public int getLane() {
        return lane;
    }

    public void setLane(int lane) {
        this.lane = lane;
    }

    public double getTargetVelocity() {
        return targetVelocity;
    }

    public void setTargetVelocity(double targetVelocity) {
        this.targetVelocity = targetVelocity;
    }

    public int getTargetLane() {
        return targetLane;
    }

    public void setTargetLane(int targetLane) {
        this.targetLane = targetLane;
    }

    public boolean isInFollowMeMode() {
        return followMeMode;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isFollowMeMode() {
        return followMeMode;
    }

    public void setFollowMeMode(boolean followMeMode) {
        this.followMeMode = followMeMode;
    }
}
