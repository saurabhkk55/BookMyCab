package com.saurabhorg.uber.uberApllication.advices;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {
    // @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime timeStamp;
    private T data;
    private ApiError error;

    public ApiResponse() {
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponse(T data) {
        this(); // same as, ApiResponse();
        this.data = data;
        this.error = null;
    }

    public ApiResponse(ApiError error) {
        this(); // same as, ApiResponse();
        this.error = error;
        this.data = null;
    }
}
