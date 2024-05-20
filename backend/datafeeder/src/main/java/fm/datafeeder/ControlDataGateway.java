package fm.datafeeder;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleDataDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ControlDataGateway {

    private static final Logger logger = LoggerFactory.getLogger(ControlDataGateway.class);
    private final SimulationManager simulationManager;
    private final DataSenderService dataSenderService;

    public ControlDataGateway(SimulationManager simulationManager, DataSenderService dataSenderService) {
        this.simulationManager = simulationManager;
        this.dataSenderService = dataSenderService;
    }

    @RabbitListener(queues = "#{controlDataQueue.name}")
    public void receiveData(VehicleStatusDTO statusDTO) {
        simulationManager.update(statusDTO);
    }

    @Scheduled(fixedRate = 2000L)
    public void sendStatus() {
        List<Vehicle> vehicles = simulationManager.getVehicles();
        for (Vehicle vehicle : vehicles) {
            logger.info("Sending status for {}: vel: {}, lane: {}, isLeadingVehicle: {}, followMeMode Active: {}", vehicle.getVin(), vehicle.getVelocity(), vehicle.getLane(), vehicle.isLeadingVehicle(), vehicle.isFollowMeMode());
            update(2, vehicle);
            var data = mapVehicleDataDto(vehicle);
            dataSenderService.sendData(data);
            logger.info("sendStatus({})", data);
        }
    }

    private void update(double dtSeconds, Vehicle vehicle) {
        double speedKmPerSecond = vehicle.getVelocity() / 3_600;
        double dxKm = speedKmPerSecond * dtSeconds;
        double newLat = vehicle.getLocation().getLatitude() + (dxKm / 6378) * (180 / Math.PI);
        vehicle.getLocation().setLatitude(newLat);
        logger.debug("updating position. New Position ({},{})", vehicle.getLocation().getLongitude(), vehicle.getLocation().getLatitude());
    }

    private VehicleDataDTO mapVehicleDataDto(Vehicle vehicle) {

        TargetControlDTO targetControlDTO = null;
        if(vehicle.isLeadingVehicle() && vehicle.isFollowMeMode()) {
            targetControlDTO = new TargetControlDTO(vehicle.getVelocity(), vehicle.getLane());
        }

        return new VehicleDataDTO(
                vehicle.getVin(),
                vehicle.getLocation(),
                vehicle.getVelocity(),
                vehicle.getLane(),
                targetControlDTO,
                LocalDateTime.now()
        );
    }
}
