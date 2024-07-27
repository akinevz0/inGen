package com.akinevz;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.template.TemplateFile;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = { "template" }, commandDescription = "Generate template in working directory")
public class TemplateCommand implements Callable<Integer> {
    static final Logger logger = Logger.getLogger(TemplateCommand.class.getName());
    @Parameter(names = { "-h", "--help" }, help = true)
    volatile private boolean help = false;

    @Override
    public Integer call() throws Exception {
        if (help) {
            final var jcommander = new JCommander(this);
            jcommander.setProgramName("ingen template");
            jcommander.usage();
            return -1;
        }

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