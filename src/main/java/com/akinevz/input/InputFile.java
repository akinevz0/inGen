package com.akinevz.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

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

        final var yaml = new Yaml(new Constructor(InputYaml.class, new LoaderOptions()));
        final var fis = Files.newInputStream(path);
        final var loaded = yaml.<InputYaml>load(fis);
        // try {
        // for (final String holeString : tf.getHoles()) {
        // if (!loaded.has(holeString))
        // throw new TemplatingException(holeString);
        // }
        // } catch (final TemplatingException ex) {
        // throw ex;
        // } catch (final Exception ex) {
        // throw new TemplatingException(ex);
        // }
        objects = loaded;
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
