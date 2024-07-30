package com.akinevz.install.platform;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.PackageInstallException;

public class ManagedInstall implements IPackageManager {

    private final static Logger logger = Logger.getLogger(ManagedInstall.class.getName());

    private final String installName;
    private final ManagedInstallAdapter packages;

    public ManagedInstall(final String name) {
        this.installName = name;
        this.packages = new ManagedInstallAdapter();
    }

    @Override
    public boolean hasInstalled(final String packageName) throws IOException, InterruptedException {
        return packages.contains(packageName);
    }

    @Override
    public String getName() {
        return installName;
    }

    @Override
    public void install(final String packageName) throws PackageInstallException {
        logger.log(Level.WARNING, "Registering locally installed package {0}", packageName);
        throw new UnsupportedOperationException("Unsupported method 'install' for a managed install");
    }

}
