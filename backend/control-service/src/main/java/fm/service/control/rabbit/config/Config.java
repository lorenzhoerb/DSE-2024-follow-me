package fm.service.control.rabbit.config;

import fm.service.control.rabbit.consumer.CustomMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Value("${exchange.name}")
    private String exchange;
    @Value("${fromInventory.queue.name}")
    private String fromInventory;
    @Value("${fromInventory.key.name}")
    private String fromInventoryKey;
    @Value("${requestInfo.queue.name}")
    private String request;
    @Value("${requestInfo.key.name}")
    private String requestKey;
    @Value("${respondInfo.queue.name}")
    private String respond;
    @Value("${respondInfo.key.name}")
    private String respondKey;

    @Bean
    public Queue queueInventory() {
        return new AnonymousQueue();
    }

    @Bean
    public Queue queueRequest() {
        return new Queue(request);
    }

    @Bean
    public Queue queueRespond() {
        return new Queue(respond);
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
    public Binding bindingRequest() {
        return BindingBuilder.bind(queueRequest()).to(topicExchange()).with(requestKey);
    }

    @Bean
    public Binding bindingRespond() {
        return BindingBuilder.bind(queueRespond()).to(topicExchange()).with(respondKey);
    }

    @Bean
    public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(CustomMessageListener listener) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener, "handleMessage");
        adapter.setMessageConverter(converter());
        return adapter;
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

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public HttpHeaders headers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }
}
