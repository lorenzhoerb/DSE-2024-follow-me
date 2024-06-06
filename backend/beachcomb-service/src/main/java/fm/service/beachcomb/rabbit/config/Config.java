package fm.service.beachcomb.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${exchange.name}")
    private String exchange;
    @Value("${fromDatafeeder.queue.name}")
    private String fromDatafeeder;
    @Value("${fromInventory.key.name}")
    private String fromInventoryKey;
    @Value("${fromDatafeeder.key.name}")
    private String fromDatafeederKey;



    @Bean
    Queue beachcombVehicleEventQueue() {
        return new Queue("beachcomb.vehicle.evnet", true);
    }

    @Bean
    Binding beachcombVehicleEventQueueBinding() {
        return BindingBuilder.bind(beachcombVehicleEventQueue())
                .to(topicExchange()).with("event.vehicle.created");
    }

    @Bean
    public Queue queueInventory() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue queueDatafeeder() {
        return new Queue(fromDatafeeder);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingInventory() {
        return BindingBuilder.bind(queueInventory()).to(topicExchange()).with(fromInventoryKey);
    }

    @Bean
    public Binding bindingDatafeeder() {
        return BindingBuilder.bind(queueDatafeeder()).to(topicExchange()).with(fromDatafeederKey);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}