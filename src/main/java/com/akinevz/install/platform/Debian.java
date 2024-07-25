package com.akinevz.install.platform;

import com.akinevz.install.package_manager.IPackageManager;

public class Debian implements IPlatform {

    @Override
    public String getName() {
        return "Debian";
    }

    @Override
    public IPackageManager getPackageManager() {
        return IPackageManager.APT;
    }

}
