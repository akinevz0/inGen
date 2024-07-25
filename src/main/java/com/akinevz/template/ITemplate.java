package com.akinevz.template;

import java.nio.file.Path;

@FunctionalInterface
public interface ITemplate {
    Path getPath();

    default Path getSourceFolder() {
        return getPath().getParent();
    }
}
