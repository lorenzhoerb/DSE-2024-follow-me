package fm.service.beachcomb;

import fm.service.beachcomb.mongo.controller.MongoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BeachcombServiceApplication implements CommandLineRunner {

    @Autowired
    MongoController controller;

    public static void main(String[] args) {
        SpringApplication.run(BeachcombServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        controller.freshStart();
    }
}
