package com.akinevz.compiler;

public class CompilerFactory {

    private final ICompiler pandocInstance = Pandoc.instance();

    public ICompiler getInstance(String name) throws UnsupportedCompilerException {
        switch (name) {
            case "pandoc":
                return this.pandocInstance;
            default:
                throw new UnsupportedCompilerException(name);
        }
    }
    
}
