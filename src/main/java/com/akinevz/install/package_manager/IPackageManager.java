package com.akinevz.install.package_manager;

import java.io.IOException;

public interface IPackageManager {

    IPackageManager APT = new Apt();
    IPackageManager PACMAN = new Pacman();

    boolean hasInstalled(String packageName) throws IOException, InterruptedException;

    String getName();

    default void installMultiple(String[] packageNames) throws PackageInstallException {
        for (String packageString : packageNames) {
            install(packageString);
        }
    }

    void install(String packageName) throws PackageInstallException;

}
