package com.akinevz.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateFile implements ITemplate {

    private final String file;
    private final String[] lines;
    private final Path path;
    private final String holeRegex = "(?<!\\\\)\\$([^\\s$]+)\\$";
    private final Set<String> exclusions = Stream.of("endif", "endfor", "if", "for")
            .collect(Collectors.toSet());
    private final ArrayList<String> holes = new ArrayList<String>();

    public TemplateFile(final Path path) throws IOException, EmptyTemplateException {
        // we load the lines, then process the given template for replacements
        this.path = path;
        this.file = path.toString();

        if (!Files.exists(getPath()))
            throw new FileNotFoundException("template file " + file);

        this.lines = Files.lines(getPath()).toArray(String[]::new);

        final var string = String.join("\n", lines);
        final var holePattern = Pattern.compile(holeRegex);
        final var holeMatcher = holePattern.matcher(string);

        if (!holeMatcher.find())
            throw new EmptyTemplateException(path);

        do {
            final var hole = holeMatcher.group().replaceAll("\\$", "");
            if (!exclusions.stream().anyMatch(hole::startsWith))
                holes.add(hole);
        } while (holeMatcher.find());

    }

    public TemplateFile(final String[] lines, final Path dest) throws IOException, EmptyTemplateException {
        this.path = dest;
        this.file = path.toString();
        if (Files.exists(getPath()))
            throw new IOException("template file " + file + " exists, will not overwrite");

        this.lines = lines;
        final var string = String.join("\n", lines);
        final var holePattern = Pattern.compile(holeRegex);
        final var holeMatcher = holePattern.matcher(string);

        if (!holeMatcher.find())
            throw new EmptyTemplateException(path);

        do {
            final var hole = holeMatcher.group().replaceAll("\\$", "");
            if (!exclusions.stream().anyMatch(hole::startsWith))
                holes.add(hole);
        } while (holeMatcher.find());

    }

    @Override
    public String toString() {
        return file;
    }

    @Override
    public String[] getContents() {
        return lines;
    }

    @Override
    public String[] getHoles() {
        return holes.toArray(String[]::new);
    }

    @Override
    public boolean contains(final String objectKey) {
        return Arrays.stream(getHoles()).anyMatch(objectKey::equals);
    }

    public static TemplateFile fromResource(final Path resource) throws IOException, EmptyTemplateException {
        final var resourceName = resource.getFileName().toString();
        final var inputStream = ClassLoader.getSystemResourceAsStream(resourceName);
        final var reader = new BufferedReader(new InputStreamReader(inputStream));
        final var lines = reader.lines().toArray(String[]::new);
        return new TemplateFile(lines, Path.of(resourceName));
    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public void save() throws IOException {
        if (path == null)
            throw new IOException(this + " is immutable");
        save(path);
    }

    public void save(final Path path) throws IOException {
        Files.writeString(path, String.join("\n", lines));
    }
}
