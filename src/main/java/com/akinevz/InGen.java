package com.akinevz;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(mixinStandardHelpOptions = true, subcommands = { TemplateCommand.class,
        CompileCommand.class }, version = "0.1", name = "ingen")
public class InGen implements Runnable {
    @Spec
    CommandSpec spec;

    @Override
    public void run() {
        spec.commandLine().usage(System.err);
    }

    public static void main(final String[] args) {
        final var command = new CommandLine(new InGen());
        final var exit = command.execute(args);
        System.exit(exit);
    }
}
