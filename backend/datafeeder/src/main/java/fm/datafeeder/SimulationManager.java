package fm.datafeeder;

import fm.api.datafeeder.VehicleStatusDTO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SimulationManager implements ISimulation {
    private final List<Simulation> simulations;

    public SimulationManager() {
        this.simulations = new ArrayList<>();
    }

    @Override
    public void update(VehicleStatusDTO status) {
        for(Simulation simulation : simulations) {
            simulation.update(status);
        }
    }

    List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Simulation s : simulations) {
            vehicles.add(s.getLeadVehicle());
            vehicles.add(s.getFollowVehicle());
        }
        return vehicles;
    }

    public void addSimulation(Simulation simulation) {
        simulations.add(simulation);
    }
}
