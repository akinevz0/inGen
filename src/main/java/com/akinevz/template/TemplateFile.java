package com.akinevz.template;

import java.nio.file.Path;

public class TemplateFile implements ITemplate {

    private final Path file;

    public TemplateFile(final String filename) {
        this.file = Path.of(filename);
    }

    public TemplateFile(final Path file) {
        this.file = file;
    }

    @Override
    public Path getPath() {
        return file;
    }

}
