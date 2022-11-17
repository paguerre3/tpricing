package com.capitol.pricing.exceptions;

import java.time.LocalDate;

public class RestError {
    private LocalDate localDate;
    private String message;
    private String details;

    /**
     * Constructor isn't exposed above class scope as it's only being used inside builder.
     *
     * @param localDate LocalDate
     * @param message String
     * @param details details
     */
    private RestError(LocalDate localDate, String message, String details) {
        this.localDate = localDate;
        this.message = message;
        this.details = details;
    }

    public LocalDate getLocalDate() {
        return localDate;
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
        private LocalDate localDate;
        private String message;
        private String details;

        public Builder setLocalDate(LocalDate localDate) {
            this.localDate = localDate;
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
            return new RestError(this.localDate == null ? LocalDate.now() : this.localDate,
                    this.message, this.details);
        }
    }
}



