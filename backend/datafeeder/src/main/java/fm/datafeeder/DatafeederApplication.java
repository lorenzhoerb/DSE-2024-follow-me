package fm.datafeeder;

import fm.api.common.EventMessageDTO;
import fm.api.datafeeder.Location;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DatafeederApplication implements CommandLineRunner {

    private final SimulationManager simulationManager;
    private final EventMessageService eventMessageService;

    public DatafeederApplication(SimulationManager simulationManager, EventMessageService eventMessageService) {
        this.simulationManager = simulationManager;
        this.eventMessageService = eventMessageService;
    }

    public static void main(String[] args) {
        SpringApplication.run(DatafeederApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Vehicle v1 = new Vehicle();
        v1.setVin("MEC-X1-1000");
        v1.setVelocity(100);
        v1.setLane(1);
        v1.setLocation(new Location(48.200467, 16.3678796));
        Vehicle v2 = new Vehicle();
        v2.setVin("MEC-X1-1001");
        v2.setVelocity(50);
        v2.setLane(1);
        v2.setLocation(new Location(48.200467, 16.3678796));
        Simulation s = new Simulation(eventMessageService, v2, v1);
        simulationManager.addSimulation(s);
        eventMessageService.sendEvent(new EventMessageDTO("Simulation for vehicles " + v1.getVin() + " and " + v2.getVin() + "started."));
    }
}
