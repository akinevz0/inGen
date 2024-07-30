package com.akinevz.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.akinevz.template.TemplateFile;

public class InputFile {

    private final Path path;
    private final String file;
    private final Map<String, Object> objects;

    public InputFile(final Path path, final TemplateFile tf) throws IOException, TemplatingException,
            NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        this.path = path;
        this.file = path.toString();

        if (!Files.exists(getPath()))
            throw new FileNotFoundException("input file " + file);

        final var yaml = new Yaml();
        final var fis = Files.newInputStream(path);
        objects = yaml.load(fis);
    }

    private Path getPath() {
        return path;
    }

    public Map<String, Object> getObjects() {
        return objects;
    }

    @Override
    public String toString() {
        return file;
    }

}
