package dse.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(value = "dse.rabbitmq")
@Data
public class RabbitMqProperties {
    private String exchange;
    private String routingKey;
    private String queue;
}
