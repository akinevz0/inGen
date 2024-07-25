package com.akinevz.compiler;

import java.util.Optional;

public class Pandoc implements ICompiler {

    private static Optional<Pandoc> instance;

    public static Pandoc instance() {
        if (!instance.isPresent()) {
            instance = Optional.of(new Pandoc());
        }
        return instance.get();
    }

    private String compilerCommand;

    public Pandoc() {
        super();
    }

    @Override
    public String getCompilerName() {
        return "pandoc";
    }

    @Override
    public String getCompilerCommand() {
        return this.compilerCommand;
    }

}
