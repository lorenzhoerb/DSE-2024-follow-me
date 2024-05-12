package fm.datafeeder;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${rabbit.exchange.vehicle}")
    private String vehicleExchange;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(vehicleExchange);
    }

    @Bean
    public AnonymousQueue controlDataQueue() {
       return new AnonymousQueue();
    }

    @Bean
    public Binding declareControlQueueBinding(@Value("${rabbit.key.data.vehicle}") String routingKey) {
        return BindingBuilder.bind(controlDataQueue()).to(topicExchange()).with(routingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}
