package ro.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Dynamically adds a new file URL to the classPath
 */
public class MyClassLoader extends URLClassLoader {
    public MyClassLoader() {
        super(new URL[0], ClassLoader.getSystemClassLoader());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }
}
