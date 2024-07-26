package com.akinevz.compiler;

import java.nio.file.Path;

import com.akinevz.compiler.CompilerFactory.InstanceType;

public abstract class LocalCompiler implements ICompiler {

    private final String name;
    private final String command;

    LocalCompiler(final InstanceType type) {
        this.name = type.getName();
        this.command = type.getName();
    }

    public LocalCompiler(final InstanceType type, final Path path) {
        this.name = type.getName();
        this.command = path.toAbsolutePath().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCommand() {
        return command;
    }

}
