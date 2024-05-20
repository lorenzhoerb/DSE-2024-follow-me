package fm.service.event;
import fm.api.common.EventMessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class EventGateway {

    private final EventService eventService;

    public EventGateway(EventService eventService) {
        this.eventService = eventService;
    }

    @RabbitListener(queues = "#{eventQueue.name}")
    public void handleEvent(EventMessageDTO eventMessageDTO) {
        eventService.createEvent(eventMessageDTO);
    }
}
