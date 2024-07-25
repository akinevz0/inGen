package com.akinevz.compiler;

public class FileNotExecutableException extends Exception {

    public FileNotExecutableException(final String path) {
        super(path + " is not executable");
    }

}
