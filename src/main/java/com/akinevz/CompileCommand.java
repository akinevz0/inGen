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

@Command(name = "compile", mixinStandardHelpOptions = true, description = "generate an invoice using default.invoice")
public class CompileCommand implements Callable<Integer> {

    static final Logger logger = Logger.getLogger(CompileCommand.class.getName());

    @Parameters(index = "0", description = "Input file path")
    private Path inPath;

    @Parameters(index = "1", description = "Output file or Template file path", defaultValue = "tex.invoice", arity = "0..1")
    private Path tfPath;

    @Parameters(index = "2", description = "Output file path (see pandoc manual for supported formats)", arity = "0..1")
    private Path outPath;

    @Override
    public Integer call() throws Exception {
        // package management
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

        // check if infile exists which should be a YAML
        // TODO: parse yaml
        final var in = new InputFile(inPath);
        logger.log(Level.INFO, in + " loaded");
        // load template
        final var tf = new TemplateFile(tfPath);
        logger.log(Level.INFO, tf + " loaded");

        // acquire handle to pandoc
        final var c = CompilerFactory.getCompiler(InstanceType.Pandoc);
        logger.log(Level.INFO, "Pandoc loaded");

        // get output path
        final var out = (outPath == null) ? makeOutput() : outPath;
        logger.log(Level.INFO, "Writing to " + out.toString());

        // compile template
        c.compile(inPath, tf, out);
        return 0;
    }

    private Path makeOutput() {
        final var filename = inPath.getFileName().toString();
        final var output = inPath.resolveSibling(filename + ".pdf");
        return output;
    }

    public static void main(final String[] args) {
        final int exit = new CommandLine(new CompileCommand()).execute(args);
        System.exit(exit);
    }

}
