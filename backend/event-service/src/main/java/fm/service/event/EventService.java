package fm.service.event;

import fm.api.common.EventMessageDTO;
import fm.api.common.LogLevel;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private static final Logger log = LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public EventService(EventRepository eventRepository, SimpMessagingTemplate simpMessagingTemplate) {
        this.eventRepository = eventRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    public void createEvent(EventMessageDTO event) {
        log.info("createEvent{}", event);
        Event eventEntity = Event.builder()
                .message(event.getMessage())
                .timestamp(event.getTimestamp())
                .logLevel(event.getLogLevel())
                .build();
        eventRepository.save(eventEntity);
        simpMessagingTemplate.convertAndSend("/topic/events", event);
    }

    public List<EventMessageDTO> findAll(Optional<LogLevel> logLevel) {
        log.info("findAll({})", logLevel);
        var all = eventRepository.findAll();
        List<Event> events = logLevel.isPresent() ? eventRepository.findByLogLevelOrderByTimestampDesc(logLevel.get()) : eventRepository.findAllByOrderByTimestampDesc();
        return events.stream()
                .map(this::mapEventToEventMessageDTO)
                .toList();
    }

    public EventMessageDTO mapEventToEventMessageDTO(Event event) {
        return new EventMessageDTO(
                event.getMessage(),
                event.getLogLevel(),
                event.getTimestamp()
        );
    }
}
