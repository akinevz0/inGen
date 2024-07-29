package com.akinevz.compiler;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.Command;
import com.akinevz.compiler.CompilerFactory.InstanceType;
import com.akinevz.input.InputFile;
import com.akinevz.template.TemplateFile;

public class Pandoc extends LocalCompiler implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(Pandoc.class.getName());

    private final ExecutorService es = Executors.newCachedThreadPool();

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

    @Override
    public int compile(final InputFile in, final TemplateFile tf, final Path out)
            throws IOException, InterruptedException, ExecutionException {

        final var command = new Command(es, getCommand(), "-i", in.toString(), "--template", tf.toString(), "-o",
                out.toString());
        if (command.getExitCode() != 0) {
            logger.log(Level.SEVERE, "error:\n{0}", String.join("\n", command.getError()));
        }
        logger.log(Level.INFO, "complete.\n{0}", String.join("\n", command.getOutput()));
        return 0;
    }

    @Override
    public void close() throws Exception {
        es.shutdown();
    }

}
