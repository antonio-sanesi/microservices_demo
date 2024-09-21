package it.asanesi.order_service.error;

import lombok.Data;

@Data
public class ErrorDto {
    private int status;
    private String message;
    private long timeStamp = System.currentTimeMillis();
}