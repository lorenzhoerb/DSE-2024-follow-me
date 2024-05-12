package fm.api.common;

public record RestErrorDTO(
        Long status,
        String message

) {
}
