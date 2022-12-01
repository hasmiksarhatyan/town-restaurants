package am.itspace.townrestaurantsrest.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @Schema(description = "Http response status", example = "CONFLICT")
    private HttpStatus status;

    @Schema(description = "The exception description", example = "4121")
    private Integer errorCode;

    @Schema(description = "Timestamp showing when the error occurred")
    private LocalDateTime timestamp;

    @Schema(description = "Error message", example = "Object not found")
    private String message;
}
