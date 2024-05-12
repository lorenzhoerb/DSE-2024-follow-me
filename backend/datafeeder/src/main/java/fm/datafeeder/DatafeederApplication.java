package fm.datafeeder;

import fm.api.datafeeder.Location;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatafeederApplication implements CommandLineRunner {

    private final SimulationManager simulationManager;

    public DatafeederApplication(SimulationManager simulationManager) {
        this.simulationManager = simulationManager;
    }

    public static void main(String[] args) {
        SpringApplication.run(DatafeederApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Vehicle v1 = new Vehicle();
        v1.setVin("a");
        v1.setVelocity(100);
        v1.setLane(1);
        v1.setLocation(new Location(48.200467, 16.3678796));
        Vehicle v2 = new Vehicle();
        v2.setVin("b");
        v2.setVelocity(50);
        v2.setLane(1);
        v2.setLocation(new Location(48.200467, 16.3678796));
        Simulation s = new Simulation(v1, v2);
        simulationManager.addSimulation(s);
    }
}
