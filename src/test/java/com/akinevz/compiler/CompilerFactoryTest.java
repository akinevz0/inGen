package com.akinevz.compiler;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    void testGetLocal() throws IOException {
        assertThrows(Exception.class,
                () -> CompilerFactory.getLocal(InstanceType.Pandoc, localPandoc));

        final var usrBinPandoc = Path.of("/usr/bin/pandoc");
        Files.createSymbolicLink(localPandoc, usrBinPandoc);

        final var compiler = assertDoesNotThrow(() -> CompilerFactory.getLocal(InstanceType.Pandoc, localPandoc));

        assertInstanceOf(ICompiler.class, compiler);
    }

}
