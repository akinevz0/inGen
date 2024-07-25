package com.akinevz.compiler;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompilerFactory {

    public static enum InstanceType {
        Pandoc("pandoc");

        private final String string;

        InstanceType(final String string) {
            this.string = string;
        }

        String getName() {
            return string;
        }
    }

    private static final Logger logger = Logger.getLogger(CompilerFactory.class.getName());
    private static final CompilerFactory instance = new CompilerFactory();
    private static final ICompiler pandocInstance = Pandoc.instance();

    private ICompiler getInstance(final InstanceType type) throws UnsupportedCompilerException {
        return switch (type) {
            case Pandoc -> pandocInstance;
            default -> throw new UnsupportedCompilerException(type);
        };
    }

    public static ICompiler getLocal(final InstanceType type, final String path)
            throws FileNotFoundException, FileNotExecutableException {
        return getLocal(type, Path.of(path));
    }

    public static ICompiler getLocal(final InstanceType type, final Path path)
            throws FileNotFoundException, FileNotExecutableException {
        if (!Files.exists(path))
            throw new FileNotFoundException(path.toString());

        if (!Files.isExecutable(path))
            throw new FileNotExecutableException(path.toString());

        return new LocalCompiler(type, path);
    }

    public static ICompiler getCompiler(final InstanceType instanceType) {
        try {
            return instance.getInstance(instanceType);
        } catch (final UnsupportedCompilerException e) {
            logger.log(Level.WARNING, "Invalid argument: ", e);
            return null;
        }
    }

}
