package com.akinevz;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.compiler.CompilerFactory;
import com.akinevz.compiler.CompilerFactory.InstanceType;
import com.akinevz.install.DependenciesUnsatisfiedException;
import com.akinevz.install.DependencyResolver;
import com.akinevz.install.PlatformUnupportedException;
import com.akinevz.template.TemplateFile;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = { "compile" }, commandDescription = "Compile an invoice with a template")
public class CompileCommand implements Callable<Integer> {

    static final Logger logger = Logger.getLogger(CompileCommand.class.getName());

    @Parameter(description = "Input file(s)")
    volatile private List<Path> inPaths = new ArrayList<>();

    @Parameter(names = { "-t", "--template" }, description = "Template (pandoc style)", arity = 1, required = false)
    volatile private Path tfPath = Path.of("default.invoice");

    @Parameter(names = { "-o", "--out" }, description = "Output folder", arity = 1, required = false)
    volatile private Path outPath = Path.of(".");

    @Override
    public Integer call() throws Exception {
        // package management
        final var packageNames = new String[] { "texlive-latex-extra", "pandoc" };
        try (final var dr = new DependencyResolver()) {
            if (!dr.ensureHas(packageNames)) {
                throw new DependenciesUnsatisfiedException(packageNames);
            }
            logger.log(Level.INFO, "all packages installed");
        } catch (DependenciesUnsatisfiedException | PlatformUnupportedException ex) {
            logger.log(Level.WARNING, "Missing system packages: ", ex);
            return -1;
        }

        // load template
        final var tf = new TemplateFile(tfPath);
        logger.log(Level.INFO, "{0} loaded", tf);

        // acquire handle to pandoc
        final var c = CompilerFactory.getCompiler(InstanceType.Pandoc);
        logger.log(Level.INFO, "pandoc loaded");

        if (inPaths.isEmpty())
            logger.log(Level.INFO, "no input files specified");

        var returnCode = 0;
        for (final Path inPath : inPaths) {
            final var in = new InputFile(inPath);
            logger.log(Level.INFO, "{0} loaded", in);

            final var out = outToFolder(inPath, outPath, ".pdf");
            logger.log(Level.INFO, "outputting to {0}", out);

            // compile template
            returnCode += c.compile(in, tf, out);
        }
        return returnCode;
    }

    Path outToFolder(final Path inPath, final Path outPath, final String ext) {
        final var filename = inPath.getFileName().getFileName().toString();
        final var extPos = filename.lastIndexOf(".");
        final var extRemoved = filename.substring(0, extPos < 0 ? filename.length() : extPos);
        final var output = outPath.resolve(extRemoved + ext);
        return output;
    }

}
