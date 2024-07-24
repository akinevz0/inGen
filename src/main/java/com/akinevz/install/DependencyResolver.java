package com.akinevz.install;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import com.akinevz.install.package_manager.IPackageManager;
import com.akinevz.install.package_manager.PackageInstallException;
import com.akinevz.install.platform.IPlatform;

/**
 * TODO: WHAT IF WE DONT HAVE TO INSTALL THE DEP, IF ITS LOCATABLE
 * TODO: SUPPORT CUSTOM DEPENDENCIES
 */
public class DependencyResolver {

    private static final Set<String> SUPPORTED_PACKAGE_MANAGERS = Set.of(
            "apt"
    // , "pacman"
    );

    private Optional<IPackageManager> localPackageManager;

    public DependencyResolver() throws PlatformUnupportedException, IOException {
        super();
        localPackageManager = getLocalPackageManager();
    }

    public boolean ensureHas(String... packageNames) throws IOException, InterruptedException {
        var packageManager = localPackageManager.get();
        for (String pkg : packageNames) {
            if (!packageManager.hasInstalled(pkg))
                return false;
        }
        return true;
    }

    public void install(String... packageNames) throws PackageInstallException {
        var packageManager = localPackageManager.get();
        packageManager.installMultiple(packageNames);
    }

    private Optional<IPackageManager> getLocalPackageManager() throws PlatformUnupportedException, IOException {
        var platform = getPlatform();
        var supported = getSupportedPackageManagers();

        var packageManagerName = platform
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

}
