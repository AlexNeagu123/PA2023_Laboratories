package ro.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.exception.InvalidDirectory;
import ro.exception.InvalidJar;
import ro.explorer.Explorer;
import ro.explorer.JarExplorer;
import ro.loader.MyClassLoader;
import ro.util.ClassAnalysis;
import ro.util.PathUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This command loads every <p>.class</p> file inside a specified <i>.jar</i> file, identifying the packages and the classpath dynamically
 * <p>
 * All the classes annotated with @Test are being analyzed
 * <p>
 * For each class,
 * All the methods (static or not), with arguments of type <i>int</i> or <i>String</i>, annotated with @Test are being called.
 * <p>
 * Detailed statistics about the tests are printed on the screen
 *
 * @author Alex Neagu
 */
@RequiredArgsConstructor
@Log4j2
public class InspectJarCommand implements Command {
    private final String jarPath;
    private final MyClassLoader myLoader;
    private final Explorer explorer;

    public InspectJarCommand(String jarPath) {
        this.jarPath = jarPath;
        this.myLoader = new MyClassLoader();
        this.explorer = new JarExplorer(jarPath);
    }

    @Override
    public void execute() throws InvalidDirectory, InvalidJar {
        try {
            addClassPath();
            List<Class<?>> loadedClasses = new ArrayList<>();
            for (String className : explorer.walk()) {
                loadedClasses.add(loadClass(getClassSymbolicName(className)));
            }
            for (Class<?> clazz : loadedClasses) {
                if (clazz == null || !ClassAnalysis.classContainsTestAnnotation(clazz)) {
                    continue;
                }
                ClassAnalysis.invokeAllTestMethods(clazz);
            }
        } catch (IOException ex) {
            throw new InvalidJar(jarPath);
        }
    }

    private Class<?> loadClass(String classSymbolicName) {
        try {
            return this.myLoader.loadClass(classSymbolicName);
        } catch (ClassNotFoundException | NoClassDefFoundError | IllegalAccessError e) {
            log.error(e.getMessage() + " class was not found");
            return null;
        }
    }

    private String getClassSymbolicName(String className) {
        String classSymbolicName = className.replaceAll("/", ".");
        return classSymbolicName.replace(".class", "");
    }

    private void addClassPath() throws MalformedURLException {
        URL classPathURL = PathUtils.getURLFromClassPath(this.jarPath);
        myLoader.addURL(classPathURL);
    }
}
