package com.akinevz.template;

import java.io.IOException;
import java.nio.file.Path;

public interface ITemplate {

    Path getPath();

    String[] getContents();

    String[] getHoles();

    void save() throws IOException;

    boolean contains(String objectKey);

}
