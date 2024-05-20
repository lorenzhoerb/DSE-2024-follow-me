package fm.service.event;

import fm.api.common.EventMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/event")
    @SendTo("/topic/events")
    public EventMessageDTO send(EventMessageDTO event) {
        return event;
    }
}
