package com.akinevz.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TemplateFile implements ITemplate {

    private final Path path;
    private final String file;

    public TemplateFile(final Path path) throws IOException {
        this.path = path;
        this.file = path.toString();

        if (!Files.exists(getPath()))
            throw new FileNotFoundException(file);

        final var lines = Files.lines(getPath()).toArray(String[]::new);
        // do we want to pre-process the template?
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Template " + path;
    }
}
