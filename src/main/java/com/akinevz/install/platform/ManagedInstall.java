package com.akinevz.install.platform;

import java.io.IOException;

import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.PackageInstallException;

public class ManagedInstall implements IPackageManager {

    private String installName;
    private ManagedInstallAdapter packages;

    public ManagedInstall(String name) {
        this.installName = name;
        this.packages = new ManagedInstallAdapter();
    }

    @Override
    public boolean hasInstalled(String packageName) throws IOException, InterruptedException {
        return packages.contains(packageName);
    }

    @Override
    public String getName() {
        return installName;
    }

    @Override
    public void install(String packageName) throws PackageInstallException {
        throw new UnsupportedOperationException("Unsupported method 'install' for a managed install");
    }

}
