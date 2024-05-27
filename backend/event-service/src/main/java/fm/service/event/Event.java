package fm.service.event;

import fm.api.common.LogLevel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "events")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
    @Id
    private String id;
    private String message;
    private LogLevel logLevel;
    private LocalDateTime timestamp;
}
