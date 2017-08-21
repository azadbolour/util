/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util.rest;


import org.springframework.http.HttpStatus;

import static java.lang.String.format;

public class RuntimeRestClientException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public RuntimeRestClientException(Throwable cause) {
        this.initCause(cause);
        this.status = null;
        this.message = null;
    }

    public RuntimeRestClientException(String message, HttpStatus status) {
        this.message = message != null ? message : "";
        this.status = status;
    }

    @Override
    public String getMessage() {
        if (status != null)
            return format("%s - http status: %s [%s]", message, status, status.getReasonPhrase());
        else if (getCause() != null)
            return getCause().getMessage();
        else
            return "";
    }
}
