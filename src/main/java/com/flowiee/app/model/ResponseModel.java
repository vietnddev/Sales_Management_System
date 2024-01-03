package com.flowiee.app.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseModel<T> {
    @Data
    public static class Status {
        private boolean success;
        private String errors;
        private int code;
    }
    @Data
    public static class Metadata {
        private int pageSize;
        private int pageIndex;
        private int pageCount;
        private long totalEntity;
    }

    private Status status;
    private Metadata metadata;
    private List<T> elements;
}