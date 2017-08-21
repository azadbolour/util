/*
 * Copyright 2017 Azad Bolour
 * Licensed under MIT (https://github.com/azadbolour/util/blob/master/LICENSE)
 */

package com.bolour.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

/**
 * Utilities for files and resources.
 */
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);
    private FileUtil() {}

    public static String readFileAsString(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String getResourcePath(Class<?> theClass, String pathRelativeToResourcesDir) throws IOException {
        URL url = theClass.getClassLoader().getResource(pathRelativeToResourcesDir);
        if (url == null)
            throw new IOException(format("resource %s not found", pathRelativeToResourcesDir));
        String path = url.getFile();
        return path;
    }

    public static String readResourceAsString(Class<?> theClass, String pathRelativeToResourcesDir) throws IOException {
        String path = getResourcePath(theClass, pathRelativeToResourcesDir);
        String contents = readFileAsString(path);
        return contents;
    }
}
