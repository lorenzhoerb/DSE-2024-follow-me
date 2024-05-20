package fm.service.event;

import fm.api.common.EventMessageDTO;
import fm.api.common.LogLevel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventMessageDTO> getAllEvents(@RequestParam("level") Optional<LogLevel> level) {
        return eventService.findAll(level);
    }
}
