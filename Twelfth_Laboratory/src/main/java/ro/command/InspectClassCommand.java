package ro.command;

import lombok.extern.log4j.Log4j2;
import ro.exception.InvalidClass;
import ro.exception.InvalidSystemPath;
import ro.loader.MyClassLoader;
import ro.util.ClassAnalysis;
import ro.util.PathUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This command loads a specified class in memory, identifying dynamically its package
 * <p>
 * Several details regarding the methods are printed on the screen, line by line
 * <p>
 * All the static methods, with no arguments, annotated with @Test are being called
 */
@Log4j2
public class InspectClassCommand implements Command {
    private final String absolutePath;
    private final MyClassLoader myLoader;

    public InspectClassCommand(String absolutePath) {
        this.absolutePath = absolutePath;
        this.myLoader = new MyClassLoader();
    }

    @Override
    public void execute() throws InvalidClass, InvalidSystemPath {
        try {
            String packageName = PathUtils.getPackageNameFromAbsolutePath(absolutePath);
            String symbolicClassName = PathUtils.getSymbolicClassNameFromAbsolutePath(absolutePath, packageName);
            String classPath = PathUtils.getClassPathFromAbsolutPath(absolutePath, symbolicClassName);

            Class<?> correspondingClass = loadClass(classPath, symbolicClassName);
            ClassAnalysis.printClassMethods(symbolicClassName, correspondingClass);
            ClassAnalysis.invokeStaticEmptyTestMethods(correspondingClass);
        } catch (IOException ex) {
            throw new InvalidSystemPath(absolutePath);
        } catch (ClassNotFoundException ex) {
            throw new InvalidClass(ex.getMessage());
        }
    }

    private Class<?> loadClass(String classPath, String symbolicClassName) throws MalformedURLException, ClassNotFoundException {
        URL classPathURL = PathUtils.getURLFromClassPath(classPath);
        myLoader.addURL(classPathURL);
        return myLoader.loadClass(symbolicClassName);
    }
}
