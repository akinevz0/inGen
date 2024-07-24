package com.akinevz.install.package_manager;

public class Pacman implements IPackageManager {

    @Override
    public boolean hasInstalled(String packageName) {
        throw new UnsupportedOperationException("Unimplemented method 'hasInstalled'");
    }

    @Override
    public String getName() {
        return "pacman";
    }

    @Override
    public void install(String packageName) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'install'");
    }

}
