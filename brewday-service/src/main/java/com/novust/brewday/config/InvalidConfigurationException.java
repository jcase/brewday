package com.novust.brewday.config;

/**
 * Created by jcase on 2/13/15
 * All Copyrights apply
 */
public class InvalidConfigurationException extends Exception {

    private static final long serialVersionUID = 1795262663386107416L;

    public InvalidConfigurationException() {
    }

    public InvalidConfigurationException(Throwable cause) {
        super(cause);
    }

    public InvalidConfigurationException(String message) {
        super(message);
    }

    public InvalidConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
