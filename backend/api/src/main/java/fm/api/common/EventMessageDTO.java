package fm.api.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.time.LocalDateTime;

public class EventMessageDTO implements Serializable {
    private String message;
    private LogLevel logLevel;
    private LocalDateTime timestamp;

    public EventMessageDTO() {
    }

    public EventMessageDTO(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.logLevel = LogLevel.INFO;
    }

    public EventMessageDTO(String message, LogLevel logLevel) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.logLevel = logLevel;
    }

    public EventMessageDTO(String message, LogLevel logLevel, LocalDateTime timestamp) {
        this.message = message;
        this.logLevel = logLevel;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }
}
