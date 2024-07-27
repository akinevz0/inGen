package com.akinevz.install.platform;

import java.util.Optional;

import com.akinevz.install.package_manager.IPackageManager;

public interface IPlatform {

    static final Debian debian = new Debian();

    String getName();

    IPackageManager packageManager();

    static Optional<IPlatform> getPlatform(final String platformName) {
        return switch (platformName) {
            case "Debian GNU/Linux" -> Optional.of(debian);
            default -> Optional.empty();
        };
    }

}
