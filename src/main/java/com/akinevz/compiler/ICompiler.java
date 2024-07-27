package com.akinevz.compiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

import com.akinevz.InputFile;
import com.akinevz.template.TemplateFile;

public interface ICompiler {

    String getName();

    String getCommand();

    void compile(InputFile in, TemplateFile tf, Path out) throws IOException, InterruptedException, ExecutionException;

}
