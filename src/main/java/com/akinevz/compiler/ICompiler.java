package com.akinevz.compiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import com.akinevz.input.InputFile;
import com.akinevz.template.TemplateFile;

public interface ICompiler {

    String getName();

    String getCommand();

    int compile(final InputFile in, final TemplateFile tf, final Path out)
            throws IOException, InterruptedException, ExecutionException;

}
