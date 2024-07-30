package com.akinevz;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CommandTest {
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
        final var command = new Execute(es, "cat", "nosuchfile");
        assertEquals("cat: nosuchfile: No such file or directory", command.getError()[0]);
    }

    @Test
    void testGetExitCode() throws InterruptedException, IOException {
        final var command = new Execute(es, "echo");
        assertEquals(0, command.getExitCode());
    }

    @Test
    void testGetOutput() throws InterruptedException, ExecutionException, IOException {
        final var command = new Execute(es, "echo", "test");
        assertEquals("test", command.getOutput()[0]);
    }
}
