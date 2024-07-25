package com.akinevz.install.platform;

import com.akinevz.install.package_manager.IPackageManager;

public class UnsupportedPlatform implements IPlatform {

    private final String name;

    public UnsupportedPlatform(final String name) {
        this.name = name;
    }

    @Override
    public IPackageManager getPackageManager() {
        return new ManagedInstall(name);
    }

    @Override
    public String getName() {
        return name;
    }

}
