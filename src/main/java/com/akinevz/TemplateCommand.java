package com.akinevz;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.template.TemplateFile;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "template", mixinStandardHelpOptions = true, description = "generate a template in current working directory")
public class TemplateCommand implements Callable<Integer> {
    static final Logger logger = Logger.getLogger(TemplateCommand.class.getName());

    @Override
    public Integer call() throws Exception {
        final var source = Paths.get(this.getClass().getResource("/invoice_template.tex").getPath());
        logger.log(Level.INFO, "source path is " + source);
        final var templateFile = new TemplateFile(source);
        logger.log(Level.INFO, "acquired template file");
        final var contents = templateFile.getContents();
        final var pathName = "default.invoice";
        final var path = Path.of(pathName);
        Files.writeString(path, String.join("\n", contents));
        logger.log(Level.INFO, "finished writing template");
        return 0;
    }

    public static void main(final String[] args) {
        final int exit = new CommandLine(new TemplateCommand()).execute(args);
        System.exit(exit);
    }
}
