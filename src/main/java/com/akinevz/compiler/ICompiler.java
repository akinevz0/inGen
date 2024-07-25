package com.akinevz.compiler;

import java.nio.file.Path;

import com.akinevz.template.TemplateFile;

public interface ICompiler {

    String getName();

    String getCommand();

    void compile(Path in, TemplateFile tf, Path out);

}
