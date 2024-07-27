package com.akinevz;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.template.TemplateFile;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = { "template" }, commandDescription = "Generate template in working directory")
public class TemplateCommand implements Callable<Integer> {
    static final Logger logger = Logger.getLogger(TemplateCommand.class.getName());

    @Override
    public Integer call() throws Exception {

        final var source = Paths.get(
                this.getClass()
                        .getResource("/template.tex")
                        .getPath());
        logger.log(Level.INFO, "source path is " + source);

        final var templateFile = TemplateFile.fromResource(source);
        logger.log(Level.INFO, "acquired template file");

        final var path = Path.of("default.invoice");
        logger.log(Level.INFO, "writing template to {0}", path);

        templateFile.save(path);
        return 0;
    }

}