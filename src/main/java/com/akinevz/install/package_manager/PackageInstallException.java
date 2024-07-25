package com.akinevz.install.package_manager;

/**
 * Package <name> failed to install
 */
public class PackageInstallException extends Exception {

    public PackageInstallException(final String packageString) {
        super(packageString + " failed to install");
    }

    public PackageInstallException(final String packageString, final String... reason) {
        super(packageString + " failed to install\n" + String.join("\n", reason));
    }

    public PackageInstallException(final String packageString, final Throwable e) {
        super(packageString + " failed to install", e);
    }

}
