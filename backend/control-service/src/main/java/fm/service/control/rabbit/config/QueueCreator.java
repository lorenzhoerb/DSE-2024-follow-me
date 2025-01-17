package fm.service.control.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class QueueCreator {

    @Autowired
    RabbitAdmin admin;
    @Autowired
    TopicExchange exchange;
    @Value("${fromControl.queue.name}")
    private String fromControl;

    public void createQueueBind(String vin) {
        Queue queue = new Queue(fromControl + vin);
        admin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(exchange).with(vin);
        admin.declareBinding(binding);
    }
}
