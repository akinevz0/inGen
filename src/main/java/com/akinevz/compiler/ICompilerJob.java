package com.akinevz.compiler;

import java.nio.file.Path;

import com.akinevz.template.ITemplate;

public interface ICompilerJob {

    Path getWorkFolder();

    ITemplate getTemplate();

    Path getInput();

    Path getOutput();
}
