package fm.service.control;

import fm.api.datafeeder.TargetControlDTO;
import fm.api.datafeeder.VehicleStatusDTO;
import fm.api.inventory.VehicleType;
import fm.api.inventory.dto.VehicleBaseDTO;
import fm.service.control.Service.PairedProcessing;
import fm.service.control.Service.UnpairedProcessing;
import fm.service.control.mongo.controller.MongoController;
import fm.service.control.rabbit.producer.Producer;
import org.apache.el.ValueExpressionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ControlServiceApplication implements CommandLineRunner {

    @Autowired
    Producer producer;
    //    @Autowired
//    RestTemplate rest;
    @Autowired
    MongoController controller;
    @Autowired
    PairedProcessing pairedProcessing;
    @Autowired
    UnpairedProcessing unpairedProcessing;

    public static void main(String[] args) {
        SpringApplication.run(ControlServiceApplication.class, args);
    }

    /**
     * How often should VehicleStatus be sent? (now sent only after some change is made(paired/unpaired))
     **/

    @Override
    public void run(String... args) throws Exception {
//        List<VehicleBaseDTO> vehicles = new ArrayList<>();
//        vehicles.add(new VehicleBaseDTO("1", VehicleType.LV));
//        vehicles.add(new VehicleBaseDTO("2", VehicleType.LV));
//        vehicles.add(new VehicleBaseDTO("3", VehicleType.LV));
//        vehicles.add(new VehicleBaseDTO("4", VehicleType.LV));
//        for (VehicleBaseDTO vehicle : vehicles) {
//            producer.sendMessage(vehicle);
//        }
//        List<VehicleStatusDTO> statusList = controller.getStatusList();
//        for (VehicleStatusDTO status : statusList) {
//            System.out.println(status);
//        }
//        System.out.println("---------------------");
//        for (VehicleStatusDTO status : statusList) {
//            producer.sendStatus(status.getVin(),status);
//        }
//        producer.sendStatus(statusList.get(0).getVin(),statusList.get(0));
//        producer.sendRequestByVin("3");
//        VehicleDataDTO dataDTO = controller.findVehicleByVin("3");
//        System.out.println(dataDTO);


        /**
         * This is main functionality
         **/
        List<VehicleBaseDTO> base;
        do {
            base = controller.getBaseList();
        } while (base.size() < 2);
        while (true) {
            pairedProcessing.processing();
            unpairedProcessing.processing();
        }
   }
}
