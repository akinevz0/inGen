package com.akinevz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class InputFile {

    private final Path path;
    private final String file;
    private final Map<String, Object> objects;

    public InputFile(final Path path) throws IOException {
        this.path = path;
        this.file = path.toString();

        if (!Files.exists(getPath()))
            throw new FileNotFoundException("input file " + file);

        final var yaml = new Yaml();
        final var fis = Files.newInputStream(path);
        final var loaded = yaml.<Map<String, Object>>load(fis);
        if (loaded == null)
            this.objects = new HashMap<>();
        else
            this.objects = loaded;
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
