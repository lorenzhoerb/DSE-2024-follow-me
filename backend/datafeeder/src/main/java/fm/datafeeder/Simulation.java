package fm.datafeeder;

import fm.api.datafeeder.VehicleStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Simulation implements ISimulation {

    private static final Logger logger = LoggerFactory.getLogger(Simulation.class);

    private SimulationSate state;
    private Vehicle leadVehicle;
    private Vehicle followVehicle;

    public Simulation(Vehicle leadVehicle, Vehicle followVehicle) {
        this.leadVehicle = leadVehicle;
        this.followVehicle = followVehicle;
        this.state = SimulationSate.ENGAGEMENT;
    }

    @Override
    public synchronized void update(VehicleStatusDTO status) {
        logger.info("Received control data for {}, Simulation Status: {}", status.getVin(), state);
        Vehicle vehicle = getVehicleByVin(status.getVin());
        if(vehicle == null) return;

        updateStatus(vehicle, status);
        if (state != SimulationSate.SPEED_INC_2) {
            updateTargets(vehicle, status);
        }
        switch (state) {
            case ENGAGEMENT -> handleEngagement();
            case SPEED_INC_1 -> handleSpeedInc1();
            case SPEED_DEC_1 -> handleSpeedDec1();
            case LANE_CHANGE_1 -> handleLaneChane1();
            case LANE_CHANGE_2 -> handleLangeChange2();
            case SPEED_INC_2 -> handleSpeedIncrease2();
            case DISENGAGED -> {
            }
        }
    }

    private void updateStatus(Vehicle vehicle, VehicleStatusDTO status) {
        vehicle.setFollowMeMode(status.getFollowMeModeActive());
        if(status.getTargetControl() != null && vehicle.getVin().equals(followVehicle.getVin())) {
            vehicle.setTargetLane(status.getTargetControl().getTargetLane());
            vehicle.setTargetVelocity(status.getTargetControl().getTargetVelocity());
        }
    }

    private void updateTargets(Vehicle vehicle, VehicleStatusDTO status) {
        if(status.getTargetControl() != null && vehicle.getVin().equals(followVehicle.getVin())) {
            vehicle.setLane(status.getTargetControl().getTargetLane());
            vehicle.setVelocity(status.getTargetControl().getTargetVelocity());
        }
    }

    private void handleSpeedIncrease2() {
        if (!leadVehicle.isInFollowMeMode() && !followVehicle.isFollowMeMode()) {
            state = SimulationSate.DISENGAGED;
        }
    }

    private void handleLangeChange2() {
        if (targetsMatch()) {
            double newDisengageSpeed = leadVehicle.getVelocity() + 20;
            leadVehicle.setVelocity(newDisengageSpeed);
            state = SimulationSate.SPEED_INC_2;
            logger.info("handleLaneChane2: Switch state from {} to {}", SimulationSate.LANE_CHANGE_2, SimulationSate.SPEED_INC_2);
            logger.info("Increase velocity of LV by 20km/h. New speed target speed is {}", newDisengageSpeed);
        }
    }

    private void handleLaneChane1() {
        if (targetsMatch()) {
            int oldLane = leadVehicle.getLane();
            int newLane = calculateNewLane(leadVehicle.getLane());
            leadVehicle.setLane(newLane);
            state = SimulationSate.LANE_CHANGE_2;
            logger.info("handleLaneChane1: Switch state from {} to {}", SimulationSate.LANE_CHANGE_1, SimulationSate.LANE_CHANGE_2);
            logger.info("Switch lane from {} to {}", oldLane, newLane);
        }
    }

    private void handleSpeedDec1() {
        if (targetsMatch()) {
            int oldLane = leadVehicle.getLane();
            int newLane = calculateNewLane(leadVehicle.getLane());
            leadVehicle.setLane(newLane);
            state = SimulationSate.LANE_CHANGE_1;
            logger.info("handleSpeedDec1: Switch state from {} to {}", SimulationSate.SPEED_DEC_1, SimulationSate.LANE_CHANGE_1);
            logger.info("Switch lane from {} to {}", oldLane, newLane);
        }
    }

    private void handleSpeedInc1() {
        if (targetsMatch()) {
            double newVelocity = leadVehicle.getVelocity() - 20;
            leadVehicle.setVelocity(newVelocity);
            state = SimulationSate.SPEED_DEC_1;
            logger.info("handleSpeedInc1: Switch state from {} to {}", SimulationSate.SPEED_INC_1, SimulationSate.SPEED_DEC_1);
            logger.info("Decrease velocity of LV by 20km/h. New speed target speed is {}", newVelocity);
        }
    }

    private void handleEngagement() {
        if (leadVehicle.isInFollowMeMode() && followVehicle.isInFollowMeMode()) {
            double newVelocity = leadVehicle.getVelocity() + 20;
            leadVehicle.setVelocity(newVelocity);
            state = SimulationSate.SPEED_INC_1;
            logger.info("handleEngagement: Switch state from {} to {}", SimulationSate.ENGAGEMENT, SimulationSate.SPEED_INC_1);
            logger.info("Increase velocity of LV by 20km/h. New speed target speed is {}", newVelocity);
        }
    }

    private boolean targetsMatch() {
        return leadVehicle.getVelocity() == followVehicle.getVelocity() && followVehicle.getVelocity() == followVehicle.getTargetVelocity()
                && leadVehicle.getLane() == followVehicle.getLane() && followVehicle.getLane() == followVehicle.getTargetLane();
    }

    int calculateNewLane(int currentLane) {
        return ((currentLane + 1) % 3) + 1;
    }

    private Vehicle getVehicleByVin(String vin) {
        if(leadVehicle.getVin().endsWith(vin)) return leadVehicle;
        else if(followVehicle.getVin().endsWith(vin)) return followVehicle;
        else return null;
    }

    public Vehicle getLeadVehicle() {
        return leadVehicle;
    }

    public Vehicle getFollowVehicle() {
        return followVehicle;
    }
}
