package fm.datafeeder;

import fm.api.datafeeder.VehicleStatusDTO;

public interface ISimulation {
    /**
     * Simulation states: ENGAGEMENT -> SPEED_INC_1 -> SPEED_DEC_1 -> LANE_CHANGE_1 ->
     *     LANE_CHANGE_2 -> SPEED_INC_2 -> DISENGAGED
     * @param status
     */
    void update(VehicleStatusDTO status);
}
