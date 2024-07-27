package com.akinevz;

import java.util.concurrent.Callable;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.Parameters;

@Parameters(commandNames = "ingen", commandDescription = "generates invoices")
public class InGen implements Callable<Integer> {

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
        System.exit(ingen.call());
    }

    @Override
    public Integer call() throws Exception {
        final var jcommander = JCommander.newBuilder()
                .addObject(this)
                .addCommand(template)
                .addCommand(compile)
                .build();

        try {
            jcommander.setProgramName("ingen");
            jcommander.parse(args);
            final var retval = switch (jcommander.getParsedCommand()) {
                case "compile", "-c" -> compile.call();
                case "template", "-t" -> template.call();

                default -> -1;
            };

            if (retval != 0) {
                jcommander.usage();
            }
            return retval;

        } catch (final ParameterException e) {
            System.err.println(e.getLocalizedMessage());
        } catch (final Exception e) {
        }
        jcommander.usage();
        return -1;
    }
}
