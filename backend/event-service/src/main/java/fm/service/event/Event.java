package fm.service.event;

import fm.api.common.LogLevel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @Enumerated(EnumType.ORDINAL)
    private LogLevel logLevel;
    private LocalDateTime timestamp;
}
