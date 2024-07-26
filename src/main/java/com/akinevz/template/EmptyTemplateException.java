package com.akinevz.template;

import java.nio.file.Path;

public class EmptyTemplateException extends Exception {

    public EmptyTemplateException(final Path path) {
        super("path " + path.toString() + " appears to be empty");
    }

}
