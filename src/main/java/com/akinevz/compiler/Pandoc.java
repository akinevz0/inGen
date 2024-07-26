package com.akinevz.compiler;

import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.compiler.CompilerFactory.InstanceType;
import com.akinevz.template.TemplateFile;

public class Pandoc extends LocalCompiler {

    private static final Logger logger = Logger.getLogger(Pandoc.class.getName());

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
    public void compile(final Path in, final TemplateFile tf, final Path out) {
        final var holes = tf.getHoles();
        for (final String string : holes) {
            logger.log(Level.INFO, string);
        }
    }

}
