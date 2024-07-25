package com.akinevz.install;

import java.util.Arrays;

public class DependenciesUnsatisfiedException extends Exception {
    public DependenciesUnsatisfiedException(String[] dependencies) {
        super("Please install " + Arrays.toString(dependencies));
    }
}
