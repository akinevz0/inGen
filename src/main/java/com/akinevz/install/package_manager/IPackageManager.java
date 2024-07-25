package com.akinevz.install.package_manager;

import java.io.IOException;

public interface IPackageManager {

    boolean hasInstalled(String packageName) throws IOException, InterruptedException;

    String getName();

    default void installMultiple(final String[] packageNames) throws PackageInstallException {
        for (final String packageString : packageNames) {
            install(packageString);
        }
    }

    void install(String packageName) throws PackageInstallException;

}
