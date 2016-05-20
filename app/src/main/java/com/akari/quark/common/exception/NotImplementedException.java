package com.akari.quark.common.exception;

public class NotImplementedException extends FatalException {
    public NotImplementedException() {
        super("This method not implemented yet!");
    }
}
