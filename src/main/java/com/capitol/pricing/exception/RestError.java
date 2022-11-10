package com.capitol.pricing.except;

import java.time.LocalDate;
import java.util.Date;

public class ErrorDisplay {
    private LocalDate timestamp;
    private String message;
    private String details;

    public ErrorDisplay(LocalDate timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
