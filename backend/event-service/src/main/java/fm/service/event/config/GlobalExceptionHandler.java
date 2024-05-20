package fm.service.event.config;

import fm.api.common.ConflictException;
import fm.api.common.EntityNotFoundException;
import fm.api.common.RestErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        var status = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(status)
                .body(new RestErrorDTO((long) status.value(), ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<RestErrorDTO> handleConflictException(ConflictException ex) {
        var status = HttpStatus.CONFLICT;
        return ResponseEntity.status(status)
                .body(new RestErrorDTO((long) status.value(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var responseStatus = HttpStatus.BAD_REQUEST;
        String fieldError = ex.getBindingResult().getFieldError().getDefaultMessage();
        return new ResponseEntity<>(new RestErrorDTO((long) responseStatus.value(), fieldError), responseStatus);
    }

    @ExceptionHandler(Exception.class) // Handle all other exceptions
    public ResponseEntity<RestErrorDTO> handleOtherExceptions(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return ResponseEntity.status(status)
                .body(new RestErrorDTO((long) status.value(), "An unexpected error occurred"));
    }
}
