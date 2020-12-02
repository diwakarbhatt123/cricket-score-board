package com.phonepe.cricketscoreboard.common.exception;

public class CricketGameException extends RuntimeException {
    public CricketGameException() {
    }

    public CricketGameException(String message) {
        super(message);
    }

    public CricketGameException(String message, Throwable cause) {
        super(message, cause);
    }

    public CricketGameException(Throwable cause) {
        super(cause);
    }

    public CricketGameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
