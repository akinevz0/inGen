package com.akinevz.compiler;

import java.nio.file.Path;

import com.akinevz.template.ITemplate;

public interface ICompiler {

    String getCompilerName();

    String getCompilerCommand();

    ITemplate getTemplate();

    Path getInput();

    Path getOutput();

}
