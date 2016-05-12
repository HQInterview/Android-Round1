package com.anhnguyen.hotelquicklyround1.data.exception;

/**
 * Exception throw by the application when a User search can't return a valid result.
 */
public class DBException extends RuntimeException {

    public DBException() {
        super();
    }

    public DBException(final String message) {
        super(message);
    }

    public DBException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public DBException(final Throwable cause) {
        super(cause);
    }
}
