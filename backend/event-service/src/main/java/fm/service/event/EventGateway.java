package fm.service.event;
import fm.api.common.EventMessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventGateway {

    private static final Logger logger = LoggerFactory.getLogger(EventGateway.class);

    private final EventService eventService;

    public EventGateway(EventService eventService) {
        this.eventService = eventService;
    }

    @RabbitListener(queues = "#{eventQueue.name}")
    public void handleEvent(EventMessageDTO eventMessageDTO) {
        logger.info("handleEvent({})", eventMessageDTO);
        eventService.createEvent(eventMessageDTO);
    }
}
