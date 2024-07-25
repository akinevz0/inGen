package com.akinevz.install;

public class PlatformUnupportedException extends Exception {

    public PlatformUnupportedException() {
        super("InvoiceGen does not support your platform yet");
    }

    public PlatformUnupportedException(String string) {
        super(string);
    }

}
