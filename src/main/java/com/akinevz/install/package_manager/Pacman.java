package com.akinevz.install.package_manager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.akinevz.Command;

public class Pacman implements IPackageManager {

    private final static String PACMAN = "pacman";

    @Override
    public boolean hasInstalled(final String packageName) throws IOException, InterruptedException {
        final var command = new Command(PACMAN, "-Ql", packageName);
        return command.getExitCode() == 0;
    }

    @Override
    public String getName() {
        return "pacman";
    }

    @Override
    public void install(final String packageName) throws PackageInstallException {
        Command command;
        try {
            command = new Command(PACMAN, "-Sy", packageName);
            if (!(command.getExitCode() == 0))
                throw new PackageInstallException(packageName, command.getError());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new PackageInstallException(packageName, e);
        }

    }

}
