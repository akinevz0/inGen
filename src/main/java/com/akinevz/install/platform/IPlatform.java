package com.akinevz.install.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.SystemUtils;

import com.akinevz.install.PlatformUnupportedException;
import com.akinevz.install.package_manager.Apt;
import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.Pacman;

public interface IPlatform {

    static Optional<IPackageManager> getPackageManager(String platformName) throws PlatformUnupportedException {
        switch (platformName) {
            case "apt":
                return Optional.of(new Apt());
            case "pacman":
                return Optional.of(new Pacman());
            default:
                return Optional.empty();
        }
    }

    static Optional<IPlatform> getLocalPlatform() throws PlatformUnupportedException, IOException {
        if (SystemUtils.IS_OS_LINUX) {
            return Optional.of(getLinuxPlatformInfo());
        }
        return Optional.empty();
    }

    static IPlatform getLinuxPlatformInfo() throws IOException, PlatformUnupportedException {
        var runtime = Runtime.getRuntime();
        var getOsRelease = runtime.exec("cat /etc/os-release");
        StringBuilder output = new StringBuilder();
        BufferedReader outputReader = new BufferedReader(new InputStreamReader(getOsRelease.getInputStream()));
        outputReader.lines().forEach(line -> {
            if (line.startsWith("NAME"))
                output.append(getLinuxPlatformName(line));
        });
        String platformName = output.toString();
        switch (platformName) {
            case "Debian GNU/Linux":
                return new Debian();
            default:
                return new UnsupportedPlatform(platformName);
        }
    }

    static String getLinuxPlatformName(String line) {
        var pattern =  Pattern.compile("NAME=\"(.*)\"");
        var matcher = pattern.matcher(line);
        if(matcher.matches()){
            return matcher.group(1);
        }
        return null;
    }

    String getName();

    IPackageManager getPackageManager();

}
