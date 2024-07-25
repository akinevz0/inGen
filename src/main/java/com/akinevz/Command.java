package com.akinevz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Command {

    private final Future<String[]> output;
    private final Future<String[]> errors;
    private final Process process;

    public Command(final String... args) throws IOException {
        final var es = Main.es;
        final var builder = new ProcessBuilder(args);
        this.process = builder.start();
        final var outputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        final var errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        this.output = es.submit(() -> outputReader.lines().toArray(String[]::new));
        this.errors = es.submit(() -> errorReader.lines().toArray(String[]::new));
    }

    public int getExitCode() throws InterruptedException {
        return process.waitFor();
    }

    public String[] getError() throws InterruptedException, ExecutionException {
        return errors.get();
    }

    public String[] getOutput() throws InterruptedException, ExecutionException {
        return output.get();
    }
}
