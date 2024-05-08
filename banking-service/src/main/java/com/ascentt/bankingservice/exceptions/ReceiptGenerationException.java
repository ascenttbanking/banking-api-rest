package com.ascentt.bankservice.exceptions;

public class ReceiptGenerationException extends RuntimeException {
    public ReceiptGenerationException(String message) {
        super(message);
    }

    public ReceiptGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
