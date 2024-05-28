package fm.service.event;

import fm.api.common.EventMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "http://127.0.0.1:5173")
public class WebSocketController {

    @MessageMapping("/event")
    @SendTo("/topic/events")
    public EventMessageDTO send(EventMessageDTO event) {
        return event;
    }
}
