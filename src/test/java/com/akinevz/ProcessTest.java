package com.akinevz;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ProcessTest {
    private static ExecutorService es;

    @BeforeAll
    static void setupES() {
        es = Executors.newFixedThreadPool(2);
    }

    @AfterAll
    static void tearDownES() {
        es.shutdown();
    }

    @Test
    void testGetError() throws IOException, InterruptedException, ExecutionException {
        final var process = new Process(es, "cat", "nosuchfile");
        assertEquals("cat: nosuchfile: No such file or directory", process.getError()[0]);
    }

    @Test
    void testGetExitCode() throws InterruptedException, IOException {
        final var process = new Process(es, "echo");
        assertEquals(0, process.getExitCode());
    }

    @Test
    void testGetOutput() throws InterruptedException, ExecutionException, IOException {
        final var process = new Process(es, "echo", "test");
        assertEquals("test", process.getOutput()[0]);
    }
}
