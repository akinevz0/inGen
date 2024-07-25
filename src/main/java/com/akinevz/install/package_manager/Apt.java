package com.akinevz.install.package_manager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.akinevz.Command;

public class Apt implements IPackageManager {

    private final static String DPKG = "dpkg";

    @Override
    public boolean hasInstalled(final String packageName) throws IOException, InterruptedException {
        // TODO: check which dpkg command gets the installed package information
        final var command = new Command(DPKG, "-s", packageName);
        return command.getExitCode() == 0;
    }

    @Override
    public String getName() {
        return "apt";
    }

    @Override
    public void install(final String packageName) throws PackageInstallException {
        Command command;
        try {
            command = new Command(getName(), "install", packageName);
            if (!(command.getExitCode() == 0))
                throw new PackageInstallException(packageName, command.getError());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new PackageInstallException(packageName, e);
        }
    }

}
