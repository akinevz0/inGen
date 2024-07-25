package com.akinevz.install.package_manager;

/**
 * Package <name> failed to install
 */
public class PackageInstallException extends Exception {

    public PackageInstallException(String packageString) {
        super(packageString + " failed to install");
    }

    public PackageInstallException(String packageString, String... reason) {
        super(packageString + " failed to install\n" + String.join("\n", reason));
    }

    public PackageInstallException(String packageString, Throwable e) {
        super(packageString + " failed to install", e);
    }

}
