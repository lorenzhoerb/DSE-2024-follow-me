package fm.service.event;

import fm.api.common.EventMessageDTO;
import fm.api.common.LogLevel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @Operation(summary = "Get all events", description = "Retrieves a list of all events, optionally filtered by log level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Events retrieved successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid log level")
            })
    @GetMapping
    public List<EventMessageDTO> getAllEvents(@RequestParam("level") Optional<LogLevel> level) {
        return eventService.findAll(level);
    }
}
