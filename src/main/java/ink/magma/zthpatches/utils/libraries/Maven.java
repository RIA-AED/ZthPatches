package ink.magma.zthpatches.utils.libraries;

import java.net.MalformedURLException;
import java.net.URL;

public class Maven {
    public static URL getJarURL(Library library) {
        String part1 = library.getGroupId().replaceAll("/\\./g", "/");
        try {
            new URL("https://repo1.maven.org/maven2/net/kyori/adventure-api/4.16.0/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
