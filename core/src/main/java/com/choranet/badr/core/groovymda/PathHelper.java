/*
 * Copyright CHORA INFORMATIQUE 2010-2011
 */
package com.choranet.badr.core.groovymda;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Helper class for loading files.  Makes an attempt at interpreting 
 * {@linkplain String} paths into URLs, local jars, local files, or 
 * {@linkplain ClassLoader} resources.  
 *
 * @author     mrabbah
 * @version 1.0-SNAPSHOT
 * @created    Mars 01, 2011
 */
public class PathHelper {

    private static void validatePath(String path) {
        if (path == null) {
            throw new IllegalArgumentException("cannot process null path.");
        }

        if ("".equals(path.trim())) {
            throw new IllegalArgumentException("cannot process empty path.");
        }
    }

    static boolean containsProtocol(String path) {
        //return path.matches("^[a-zA-Z]+:.+");
        return false;
    }

    static boolean isJarFile(String path) {
        if (path.startsWith("jar:")) {
            return true;
        }

        return path.matches(".+!/.+");
    }

    /**
     * Attempts to convert a path to an InputStream.  In order, attempts to:
     * <ul>
     * <li>open the path as a URL, if it contains protocol information (e.g.
     * ftp:)
     * <li>open the path as a jarfile in the local filesystem if it includes
     * "!/".
     * <li>open the path as a local file.
     * <li>open the path as a resource using a {@linkPlain ClasLoader}.
     * </ul>
     * @param path a URI, local filesystem reference, or resource reference.
     * @return an {@linkPlain InputStream} to <code>path</code>'s content, or
     * null if the path doesn't point to something.
     * @throws IOException
     * @throws MalformedURLException if the path contains a protocol, but
     * is not a valid URL.
     */
    public static InputStream pathToStream(String path) throws IOException, MalformedURLException {
        // FIXME: attempt to load from more than one ClassLoader.
        // FIXME:
        validatePath(path);

        // A protocol is already defined, so use as-is.
        if (containsProtocol(path)) {
            return new URL(path).openStream();
        }

        if (isJarFile(path)) {
            String jarFile = path.replaceAll("(.+)!.+", "$1");
            if (!new File(jarFile).exists()) {
                throw new IOException("cannot find jar file \"" + jarFile + "\".");
            }

            return new URL("jar:file:" + path).openStream();
        }

        if (new File(path).exists()) {
            return new URL("file:" + path).openStream();
        }

        InputStream in = PathHelper.class.getResourceAsStream(path);
        if (in == null && !path.startsWith("/")) {
            in = PathHelper.class.getResourceAsStream("/" + path);
        }

        return in;
    }
}
