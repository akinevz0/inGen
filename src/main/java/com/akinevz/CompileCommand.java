package com.akinevz;

import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.compiler.CompilerFactory;
import com.akinevz.compiler.CompilerFactory.InstanceType;
import com.akinevz.install.DependenciesUnsatisfiedException;
import com.akinevz.install.DependencyResolver;
import com.akinevz.install.PlatformUnupportedException;
import com.akinevz.template.TemplateFile;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "inGen", mixinStandardHelpOptions = true, version = "0.1", description = "Generates an invoice using tex.invoice")
public class CompileCommand implements Callable<Integer> {

    static final Logger logger = Logger.getLogger(CompileCommand.class.getName());

    @Parameters(index = "0", description = "Input file path")
    private Path in;

    @Parameters(index = "1", description = "Output file or Template file path", defaultValue = "tex.invoice", arity = "0..1")
    private Path template;

    @Parameters(index = "2", description = "Output file path", arity = "0..1")
    private Path outFileName;

    @Override
    public Integer call() throws Exception {
        final var packageNames = new String[] { "texlive-latex-extra", "pandoc" };
        try {
            final var dr = new DependencyResolver();
            if (!dr.ensureHas(packageNames)) {
                throw new DependenciesUnsatisfiedException(packageNames);
            }
            logger.log(Level.INFO, "All packages installed");
        } catch (DependenciesUnsatisfiedException | PlatformUnupportedException ex) {
            logger.log(Level.WARNING, "Missing system packages: ", ex);
            return -1;
        }

        final var tf = new TemplateFile(template);
        logger.log(Level.INFO, "Template loaded");

        final var c = CompilerFactory.getCompiler(InstanceType.Pandoc);
        logger.log(Level.INFO, "Pandoc loaded");

        final var out = (outFileName == null) ? (template == null) ? makeOutput(in) : template : outFileName;
        logger.log(Level.INFO, "Writing to " + out.toString());

        c.compile(in, tf, out);
        return 0;
    }

    private Path makeOutput(final Path in) {
        final var filename = in.getFileName().toString();
        final var output = in.resolveSibling(filename + ".pdf");
        return output;
    }

    public static void main(final String[] args) {
        final int exit = new CommandLine(new CompileCommand()).execute(args);
        System.exit(exit);
    }

}
