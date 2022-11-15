package com.capitol.pricing.exceptions;

import java.time.LocalDate;

public class RestError {
    private LocalDate timestamp;
    private String message;
    private String details;

    /**
     * Constructor isn't exposed above class scope as it's only being used inside builder.
     *
     * @param timestamp LocalDate
     * @param message String
     * @param details details
     */
    private RestError(LocalDate timestamp, String message, String details) {
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

    /**
     * Builder pattern implementation.
     */
    public static class Builder {
        private LocalDate timestamp;
        private String message;
        private String details;

        public Builder setTimestamp(LocalDate timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDetails(String details) {
            this.details = details;
            return this;
        }

        public RestError build() {
            return new RestError(this.timestamp == null ? LocalDate.now() : this.timestamp,
                    this.message, this.details);
        }
    }
}



