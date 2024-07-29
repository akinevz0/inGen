package com.akinevz.input;

public class TemplatingException extends Exception {

    public TemplatingException(final String holeString) {
        super("Missing argument to template - " + holeString);
    }

    public TemplatingException(final Exception reason) {
        super(reason);
    }

}
