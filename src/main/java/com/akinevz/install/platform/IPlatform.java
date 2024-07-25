package com.akinevz.install.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SystemUtils;

import com.akinevz.install.PlatformUnupportedException;
import com.akinevz.install.package_manager.IPackageManager;

public interface IPlatform {

    static final Debian debian = new Debian();

    static Optional<IPlatform> getPlatform(final String platformName) throws PlatformUnupportedException {
        return switch (platformName) {
            case "Debian GNU/Linux" -> Optional.of(debian);
            default -> Optional.empty();
        };
    }

    static Optional<IPlatform> getLocalPlatform() throws PlatformUnupportedException, IOException {
        if (SystemUtils.IS_OS_LINUX) {
            return getLinuxPlatformName();
        }
        return Optional.empty();
    }

    static Optional<IPlatform> getLinuxPlatformName() throws IOException, PlatformUnupportedException {
        final var runtime = Runtime.getRuntime();
        final var getOsRelease = runtime.exec("cat /etc/os-release");
        final StringBuilder output = new StringBuilder();
        final BufferedReader outputReader = new BufferedReader(new InputStreamReader(getOsRelease.getInputStream()));
        outputReader.lines().forEach(line -> {
            if (line.startsWith("NAME"))
                output.append(getLinuxPlatformName(line));
        });
        final String platformName = output.toString();
        return getPlatform(platformName);
    }

    static String getLinuxPlatformName(final String line) {
        final var pattern = Pattern.compile("NAME=\"(.*)\"");
        final var matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    String getName();

    IPackageManager packageManager();

}
