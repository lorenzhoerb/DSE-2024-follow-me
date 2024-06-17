package fm.service.inventory.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private final InventoryEventProperties props;

    public RabbitMqConfig(InventoryEventProperties props) {
        this.props = props;
    }

    @Bean
    public Queue beachcombVehicleEventQueue() {
        return new Queue("beachcomb.vehicle.evnet", true);
    }

    @Bean
    public Binding beachcombVehicleEventQueueBinding() {
        return BindingBuilder.bind(beachcombVehicleEventQueue())
                .to(exchange()).with("event.vehicle.created");
    }

    @Bean
    public Queue controlVehicleEventQueue() {
        return new Queue("control.vehicle.evnet", true);
    }

    @Bean
    public Binding controlVehicleEventQueueBinding() {
        return BindingBuilder.bind(controlVehicleEventQueue())
                .to(exchange()).with("event.vehicle.created");
    }


    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(props.getExchange());
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
