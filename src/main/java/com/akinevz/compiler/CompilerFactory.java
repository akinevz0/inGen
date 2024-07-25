package com.akinevz.compiler;

public class CompilerFactory {

    private static final ICompiler pandocInstance = Pandoc.instance();

    public ICompiler getInstance(final String name) throws UnsupportedCompilerException {
        return switch (name) {
            case "pandoc" -> pandocInstance;
            default -> throw new UnsupportedCompilerException(name);
        };
    }

}
