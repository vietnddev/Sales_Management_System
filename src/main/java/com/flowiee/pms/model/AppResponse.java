package com.flowiee.pms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@JsonPropertyOrder({"success", "status", "message", "pagination", "data"})
public class AppResponse<T> implements Serializable {
    @Getter
    @Setter
    public static class PaginationModel {
        private int pageNum;
        private int pageSize;
        private int totalPage;
        private long totalElements;

        public PaginationModel(int pageNum, int pageSize, int totalPage, long totalElements) {
            this.pageNum = pageNum;
            this.pageSize = pageSize;
            this.totalPage = totalPage;
            this.totalElements = totalElements;
        }
    }

    @JsonIgnore
    @Serial
    private static final long serialVersionUID = 7702134516418120340L;

    @JsonProperty("success")
    private Boolean success;

    @JsonProperty("status")
    private HttpStatus status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    @JsonProperty("pagination")
    private PaginationModel pagination;

    public AppResponse(Boolean success, HttpStatus status, String message, T data, PaginationModel pagination) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
        this.pagination = pagination;
    }
}