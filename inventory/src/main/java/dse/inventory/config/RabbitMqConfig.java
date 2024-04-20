package dse.inventory.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    private final RabbitMqProperties properties;

    public RabbitMqConfig(RabbitMqProperties properties) {
        this.properties = properties;
    }

    @Bean
    Queue queue() {
        return new Queue(properties.getQueue(), false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(properties.getExchange());
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(properties.getRoutingKey());
    }
}
