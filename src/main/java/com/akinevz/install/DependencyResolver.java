package com.akinevz.install;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.SystemUtils;

import com.akinevz.Execute;
import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.PackageInstallException;
import com.akinevz.install.platform.IPlatform;

public class DependencyResolver implements AutoCloseable {

    private final ExecutorService es = Executors.newSingleThreadExecutor();
    private final Optional<IPlatform> platform;
    private final Optional<IPackageManager> packageManager;

    public DependencyResolver()
            throws IOException, InterruptedException, ExecutionException, PlatformUnupportedException {
        platform = getLocalPlatform();
        if (platform.isEmpty())
            throw new PlatformUnupportedException();
        packageManager = getPackageManager(platform);
    }

    private static Optional<IPackageManager> getPackageManager(final Optional<IPlatform> platform) throws IOException {
        return platform.map(IPlatform::packageManager);
    }

    Optional<IPlatform> getLocalPlatform()
            throws IOException, InterruptedException, ExecutionException {
        if (SystemUtils.IS_OS_LINUX) {
            return getLinux();
        }
        return Optional.empty();
    }

    Optional<IPlatform> getLinux()
            throws IOException, InterruptedException, ExecutionException {
        final var getOsRelease = new Execute(es, "cat", "/etc/os-release");
        final var lines = getOsRelease.getOutput();
        final var name = Stream.of(lines).filter(line -> line.startsWith("NAME")).findFirst();
        return name.map(this::getLinuxPlatformName).flatMap(IPlatform::getPlatform);

    }

    private String getLinuxPlatformName(final String line) {
        final var pattern = Pattern.compile("NAME=\"(.*)\"");
        final var matcher = pattern.matcher(line);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }

    public boolean ensureHas(final String... packageNames) throws IOException, InterruptedException {
        final var packageManager = getPackageManager();
        for (final String pkg : packageNames) {
            if (!packageManager.hasInstalled(pkg))
                return false;
        }
        return true;
    }

    public void install(final String... packageNames) throws PackageInstallException {
        final var packageManager = getPackageManager();
        packageManager.installMultiple(packageNames);
    }

    @Override
    public void close() throws Exception {
        es.shutdown();
    }

    public ExecutorService getEs() {
        return es;
    }

    public IPlatform getPlatform() {
        return platform.get();
    }

    public IPackageManager getPackageManager() {
        return packageManager.get();
    }

}
