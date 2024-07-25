package com.akinevz.compiler;

import java.util.Optional;

import com.akinevz.compiler.CompilerFactory.InstanceType;

public class Pandoc extends LocalCompiler {

    private static Optional<Pandoc> instance = Optional.empty();

    public static Pandoc instance() {
        if (!instance.isPresent()) {
            instance = Optional.of(new Pandoc());
        }
        return instance.get();
    }

    public Pandoc() {
        super(InstanceType.Pandoc);
    }

}
