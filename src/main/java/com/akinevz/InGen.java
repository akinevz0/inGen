package com.akinevz;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.commands.CompileCommand;
import com.akinevz.commands.TemplateCommand;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "ingen", commandDescription = "generates invoices")
public class InGen implements Callable<Integer> {

    private final static Logger logger = Logger.getLogger(InGen.class.getName());

    @Parameter(names = { "-h", "--help" }, help = true)
    private boolean help;

    private final String[] args;

    private final TemplateCommand template;

    private final CompileCommand compile;

    public InGen(final String[] args) {
        this.args = args;
        this.compile = new CompileCommand();
        this.template = new TemplateCommand();
    }

    public static void main(final String[] args) throws Exception {

        final var ingen = new InGen(args);
        Integer exitCode = 0;
        try {
            exitCode = ingen.call();
        } catch (final Exception e) {
            logger.log(Level.SEVERE, "unhandled exception", e);
        } finally {
            System.exit(exitCode);
        }
    }

    @Override
    public Integer call() throws Exception {
        final var jcommander = JCommander.newBuilder()
                .addObject(this)
                .addCommand(template)
                .addCommand(compile)
                .build();

        jcommander.setProgramName("ingen");
        jcommander.parse(args);
        final var command = jcommander.getParsedCommand();
        return switch (help || command == null ? "help" : command) {
            case "help" -> {
                jcommander.usage("template");
                yield -1;
            }
            case "template" -> {
                yield template.call();
            }
            case "compile" -> {
                yield compile.call();
            }
            default -> {
                jcommander.usage();
                yield -1;
            }
        };

    }
}
