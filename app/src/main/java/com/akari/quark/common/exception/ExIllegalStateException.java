package com.akari.quark.common.exception;

public class ExIllegalStateException extends IllegalStateException {
    public boolean shouldLogged;

    public ExIllegalStateException() {
        super();
    }

    public ExIllegalStateException(Throwable cause) {
        super(cause);
    }

    public ExIllegalStateException(String detailMessage) {
        super(detailMessage);
    }

    public ExIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
