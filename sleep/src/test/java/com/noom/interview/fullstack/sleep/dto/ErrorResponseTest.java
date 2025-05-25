package com.noom.interview.fullstack.sleep.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorResponseTest {

    @Test
    public void testErrorResponseConstructor() {
        // Arrange
        int status = 404;
        String error = "Not Found";
        String message = "Resource not found";
        String path = "/api/resource";
        List<String> details = List.of("Detail 1", "Detail 2");
        LocalDateTime before = LocalDateTime.now();

        // Act
        ErrorResponse response = new ErrorResponse(status, error, message, path, details);
        LocalDateTime after = LocalDateTime.now();

        // Assert
        assertThat(response.getStatus()).isEqualTo(status);
        assertThat(response.getError()).isEqualTo(error);
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getPath()).isEqualTo(path);
        assertThat(response.getDetails()).isEqualTo(details);
        assertThat(response.getTimestamp()).isNotNull();
        assertThat(response.getTimestamp()).isBetween(before, after);
    }

    @Test
    public void testErrorResponseGetters() {
        // Arrange
        int status = 400;
        String error = "Bad Request";
        String message = "Invalid input";
        String path = "/api/input";
        List<String> details = List.of("Missing field", "Invalid format");
        ErrorResponse response = new ErrorResponse(status, error, message, path, details);

        // Act & Assert
        assertThat(response.getStatus()).isEqualTo(status);
        assertThat(response.getError()).isEqualTo(error);
        assertThat(response.getMessage()).isEqualTo(message);
        assertThat(response.getPath()).isEqualTo(path);
        assertThat(response.getDetails()).isEqualTo(details);
    }
}