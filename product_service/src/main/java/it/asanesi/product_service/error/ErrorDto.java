package it.asanesi.product_service.error;

import lombok.Data;

@Data
public class ErrorDto {
    private int status;
    private String message;
    private long timeStamp = System.currentTimeMillis();
}