package com.akinevz.install.package_manager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.akinevz.Process;

public class Apt implements IPackageManager, AutoCloseable {

    private final static String DPKG = "dpkg";
    private final ExecutorService es;

    public Apt() {
        this.es = Executors.newFixedThreadPool(2);
    }

    @Override
    public boolean hasInstalled(final String packageName) throws IOException, InterruptedException {
        // TODO: check which dpkg command gets the installed package information
        final var command = new Process(es, DPKG, "-s", packageName);
        return command.getExitCode() == 0;
    }

    @Override
    public String getName() {
        return "apt";
    }

    @Override
    public void install(final String packageName) throws PackageInstallException {
        Process command;
        try {
            command = new Process(es, getName(), "install", packageName);
            if (!(command.getExitCode() == 0))
                throw new PackageInstallException(packageName, command.getError());
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new PackageInstallException(packageName, e);
        }
    }

    @Override
    public void close() throws Exception {
        es.shutdown();
    }

}
