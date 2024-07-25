package com.akinevz.compiler;

import com.akinevz.compiler.CompilerFactory.InstanceType;

public class UnsupportedCompilerException extends Exception {

    public UnsupportedCompilerException(final String name) {
        super(name);
    }

    public UnsupportedCompilerException(final InstanceType type) {
        this("Unsupported compiler: " + type.getName());
    }

}
