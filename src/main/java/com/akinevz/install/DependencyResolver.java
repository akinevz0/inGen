package com.akinevz.install;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.akinevz.compiler.CompilerFactory;
import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.PackageInstallException;
import com.akinevz.install.platform.IPlatform;

/**
 * TODO: WHAT IF WE DONT HAVE TO INSTALL THE DEP, IF ITS LOCATABLE
 * TODO: SUPPORT CUSTOM DEPENDENCIES
 */
public class DependencyResolver {

    private static final Set<String> SUPPORTED_PACKAGE_MANAGERS = Set.of(
            "apt", "pacman");

    private final Optional<IPackageManager> localPackageManager;

    private final CompilerFactory compilerFactory;

    public DependencyResolver(final String... packageNames)
            throws IOException, InterruptedException, PlatformUnupportedException, DependenciesUnsatisfiedException {
        super();
        localPackageManager = getLocalPackageManager();
        compilerFactory = new CompilerFactory();

        if (!ensureHas(packageNames)) {
            throw new DependenciesUnsatisfiedException(packageNames);
        }
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
        final var supported = getSupportedPackageManagers();

        final var packageManagerName = platform
                .map(IPlatform::getPackageManager)
                .map(IPackageManager::getName)
                .filter(supported::contains);
        if (!packageManagerName.isPresent()) {
            throw new PlatformUnupportedException();
        }
        return IPlatform.getPackageManager(packageManagerName.get());
    }

    private Set<String> getSupportedPackageManagers() {
        return SUPPORTED_PACKAGE_MANAGERS;
    }

    private Optional<IPlatform> getPlatform() throws PlatformUnupportedException, IOException {
        return IPlatform.getLocalPlatform();
    }

    public CompilerFactory getCompilerFactory() {
        return compilerFactory;
    }

}
