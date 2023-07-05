package com.rahulpateldev.exception.handler;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {

    @JsonProperty(value = "local_datetime")
    private LocalDateTime localDateTime;

    @JsonProperty(value = "http_status")
    private HttpStatus httpStatus;

    @JsonProperty(value = "status_code")
    private int statusCode;
    private String message;
    private String errors;
}
