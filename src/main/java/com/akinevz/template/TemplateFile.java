package com.akinevz.template;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TemplateFile implements ITemplate {

    private final Path path;
    private final String file;
    private final String[] lines;
    private final String[] holes;

    public TemplateFile(final Path path) throws IOException, EmptyTemplateException {
        // we load the lines, then process the given template for replacements
        this.path = path;
        this.file = path.toString();

        if (!Files.exists(getPath()))
            throw new FileNotFoundException("template file " + file);

        this.lines = Files.lines(getPath()).toArray(String[]::new);

        final var string = String.join("\n", lines);
        final var holeRegex = "(?<!\\\\)\\$([^\\s$]+)\\$";
        final var holePattern = Pattern.compile(holeRegex);
        final var holeMatcher = holePattern.matcher(string);
        if (!holeMatcher.find())
            throw new EmptyTemplateException(path);

        final var holes = new ArrayList<String>();
        final var exclusions = Stream.of("endif", "endfor", "if", "for")
                .collect(Collectors.toSet());

        do {
            final var hole = holeMatcher.group().replaceAll("\\$", "");
            if (!exclusions.stream().anyMatch(hole::startsWith))
                holes.add(hole);
        } while (holeMatcher.find());
        this.holes = holes.toArray(String[]::new);

    }

    @Override
    public Path getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Template " + path;
    }

    public String[] getContents() {
        return lines;
    }

    public String[] getHoles() {
        return holes;
    }
}
