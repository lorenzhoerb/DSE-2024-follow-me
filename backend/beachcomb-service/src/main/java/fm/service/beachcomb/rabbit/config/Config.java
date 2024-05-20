package fm.service.beachcomb.rabbit.config;

import fm.service.beachcomb.rabbit.consumer.Consumer;
import fm.service.beachcomb.rabbit.producer.Producer;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Configuration
public class Config {

    @Value("${exchange.name}")
    private String exchange;
    @Value("${fromInventory.queue.name}")
    private String fromInventory;
    @Value("${fromDatafeeder.queue.name}")
    private String fromDatafeeder;
    @Value("${respondInfo.queue.name}")
    private String respond;
    @Value("${requestInfo.queue.name}")
    private String request;
    @Value("${fromInventory.key.name}")
    private String fromInventoryKey;
    @Value("${fromDatafeeder.key.name}")
    private String fromDatafeederKey;
    @Value("${respondInfo.key.name}")
    private String respondKey;
    @Value("${requestInfo.key.name}")
    private String requestKey;

    @Bean
    public Queue queueInventory() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue queueDatafeeder() {
        return new Queue(fromDatafeeder);
    }

    @Bean
    public Queue queueRespond() {
        return new Queue(respond);
    }

    @Bean
    public Queue queueRequest() {
        return new Queue(request);
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
    public Binding bindingRespond() {
        return BindingBuilder.bind(queueRespond()).to(topicExchange()).with(respondKey);
    }

    @Bean
    public Binding bindingRequest() {
        return BindingBuilder.bind(queueRequest()).to(topicExchange()).with(requestKey);
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