package com.akinevz.compiler;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import com.akinevz.compiler.CompilerFactory.InstanceType;

public class CompilerFactoryTest {

    private static final Path localPandoc = Path.of("./pandoc");

    @AfterAll
    static void cleanup() throws IOException {
        Files.deleteIfExists(localPandoc);
    }

    @Test
    void testGetCompiler() {
        final var pandoc = CompilerFactory.getCompiler(InstanceType.Pandoc);
        assertEquals("pandoc", pandoc.getName());
        assertEquals("pandoc", pandoc.getCommand());
    }

}
