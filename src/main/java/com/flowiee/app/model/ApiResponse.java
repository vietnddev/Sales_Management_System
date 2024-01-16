package com.flowiee.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"success", "status", "message", "cause", "data"})
public class ApiResponse<T> implements Serializable {
    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 7702134516418120340L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("cause")
    private String cause;

    @JsonProperty("data")
    private T data;

    public ApiResponse(Boolean success, HttpStatus status, String message, String cause, T data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.cause = cause;
        this.data = data;
    }

    public static <T> ApiResponse<T> ok(@NonNull T data) {
        return ok("OK", data);
    }

    public static <T> ApiResponse<T> ok(@NonNull String message, @NonNull T data) {
        return ok(message, data, HttpStatus.OK);
    }

    public static <T> ApiResponse<T> ok(@NonNull String message, @NonNull T data, HttpStatus httpStatus) {
        return new ApiResponse<>(true, httpStatus, message, null, data);
    }

    public static <T> ApiResponse<T> fail(@NonNull String message, @NonNull Throwable cause) {
        return new ApiResponse<>(false, HttpStatus.INTERNAL_SERVER_ERROR, message, cause.getMessage(), null);
    }
}