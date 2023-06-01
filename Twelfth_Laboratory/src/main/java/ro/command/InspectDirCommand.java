package ro.command;

import lombok.extern.log4j.Log4j2;
import ro.exception.InvalidDirectory;
import ro.exception.InvalidSystemPath;
import ro.explorer.FolderExplorer;
import ro.loader.MyClassLoader;
import ro.util.ClassAnalysis;
import ro.util.PathUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This command loads every <p>.class</p> file inside a specified directory identifying the packages and the classpath dynamically
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
@Log4j2
public class InspectDirCommand implements Command {
    private final String directoryPath;
    private final MyClassLoader myLoader;
    private final FolderExplorer folderExplorer;
    private String classPath;

    public InspectDirCommand(String absolutePath) {
        this.directoryPath = absolutePath;
        this.myLoader = new MyClassLoader();
        this.folderExplorer = new FolderExplorer(absolutePath);
        this.classPath = null;
    }

    @Override
    public void execute() throws InvalidSystemPath, InvalidDirectory {
        try {
            List<Class<?>> loadedClasses = new ArrayList<>();
            for (String absolutePath : folderExplorer.walk()) {
                loadedClasses.add(parseClassAbsolutePath(absolutePath));
            }
            for (Class<?> clazz : loadedClasses) {
                if (clazz == null || !ClassAnalysis.classContainsTestAnnotation(clazz)) {
                    continue;
                }
                ClassAnalysis.invokeAllTestMethods(clazz);
            }
        } catch (IOException | ClassNotFoundException ex) {
            throw new InvalidSystemPath(directoryPath);
        }
    }

    private Class<?> parseClassAbsolutePath(String absolutePath) throws IOException, ClassNotFoundException {
        String packageName = PathUtils.getPackageNameFromAbsolutePath(absolutePath);
        String symbolicClassName = PathUtils.getSymbolicClassNameFromAbsolutePath(absolutePath, packageName);

        if (this.classPath == null) {
            this.classPath = PathUtils.getClassPathFromAbsolutPath(absolutePath, symbolicClassName);
            addClassPath();
        }

        return loadClass(symbolicClassName);
    }

    private Class<?> loadClass(String classSymbolicName) {
        try {
            return this.myLoader.loadClass(classSymbolicName);
        } catch (ClassNotFoundException | NoClassDefFoundError | IllegalAccessError e) {
            log.error(e.getMessage() + " class was not found");
            return null;
        }
    }

    private void addClassPath() throws MalformedURLException {
        URL classPathURL = PathUtils.getURLFromClassPath(this.classPath);
        myLoader.addURL(classPathURL);
    }
}
