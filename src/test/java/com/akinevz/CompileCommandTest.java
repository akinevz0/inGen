package com.akinevz;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;

import org.junit.Test;

public class CompileCommandTest {

    @Test
    public void testMakeOutput() {
        final var compileCommand = new CompileCommand();

        final var ext = ".pdf";
        final var thisFolder = Path.of(".");
        final var outfolder = Path.of("./out");

        final var invoicepath = Path.of("testinvoice1");
        final var testtex = Path.of("./test.tex");

        final var out = compileCommand.outToFolder(testtex, thisFolder, ext);
        final var out2 = compileCommand.outToFolder(invoicepath, outfolder, ext);

        assertEquals(Path.of("./test.pdf"), out);
        assertEquals(Path.of("./out/testinvoice1.pdf"), out2);
    }
}
