package com.akinevz.template;

import java.nio.file.Path;

public class TemplateFile {

    private final Path file;

    public TemplateFile(final String filename) {
        this.file = Path.of(filename);
    }

    public ITemplate getTemplate() {
        return () -> file;
    }

    public Path getSourceFolder() {
        return file.getParent();
    }

}
