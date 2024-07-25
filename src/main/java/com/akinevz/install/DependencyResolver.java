package com.akinevz.install;

import java.io.IOException;
import java.util.Optional;

import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.PackageInstallException;
import com.akinevz.install.platform.IPlatform;

public class DependencyResolver {

    private final Optional<IPackageManager> localPackageManager;

    public DependencyResolver()
            throws IOException, InterruptedException, PlatformUnupportedException, DependenciesUnsatisfiedException {
        super();
        localPackageManager = getLocalPackageManager();

    }

    public boolean ensureHas(final String... packageNames) throws IOException, InterruptedException {
        final var packageManager = localPackageManager.get();
        for (final String pkg : packageNames) {
            if (!packageManager.hasInstalled(pkg))
                return false;
        }
        return true;
    }

    public void install(final String... packageNames) throws PackageInstallException {
        final var packageManager = localPackageManager.get();
        packageManager.installMultiple(packageNames);
    }

    private Optional<IPackageManager> getLocalPackageManager() throws PlatformUnupportedException, IOException {
        final var platform = getPlatform();

        final var packageManager = platform
                .map(IPlatform::packageManager);

        return packageManager;
    }

    private Optional<IPlatform> getPlatform() throws PlatformUnupportedException, IOException {
        return IPlatform.getLocalPlatform();
    }

}
