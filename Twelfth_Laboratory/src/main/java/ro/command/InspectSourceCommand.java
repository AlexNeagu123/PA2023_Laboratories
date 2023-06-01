package ro.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.exception.InvalidSystemPath;
import ro.loader.MyClassLoader;
import ro.util.ClassAnalysis;
import ro.util.MockInjector;
import ro.util.PathUtils;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This command compiles a specified <p>.java</p> file, and generates the '.class' file in the same folder.
 * <p>
 * After the compilation phase, a static method called 'printSecretMessage' is injected into the '.class' file.
 * <p>
 * After the injection process, the newly created method is being called
 *
 * @author Alex Neagu
 */
@RequiredArgsConstructor
@Log4j2
public class InspectSourceCommand implements Command {
    private final String sourcePath;
    private final MyClassLoader myLoader;
    private String classFilePath;

    public InspectSourceCommand(String sourcePath) {
        this.sourcePath = sourcePath;
        this.myLoader = new MyClassLoader();
        this.classFilePath = null;
    }

    @Override
    public void execute() throws InvalidSystemPath {
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, this.sourcePath);
            this.classFilePath = this.sourcePath.replace(".java", ".class");

            String packageName = PathUtils.getPackageNameFromAbsolutePath(this.classFilePath);
            String symbolicClassName = PathUtils.getSymbolicClassNameFromAbsolutePath(this.classFilePath, packageName);
            String classPath = PathUtils.getClassPathFromAbsolutPath(this.classFilePath, symbolicClassName);
            System.out.println(symbolicClassName + " have been successfully compiled and the .class file have been created in the same folder!");

            MockInjector.injectMockMethodInClass(this.classFilePath);
            System.out.println("Method printSecretMessage was successfully injected in the newly created " + this.classFilePath + " file.");

            Class<?> correspondingClass = loadClass(classPath, symbolicClassName);
            ClassAnalysis.printClassMethods(symbolicClassName, correspondingClass);
            ClassAnalysis.invokeInjectedMethod(correspondingClass);
        } catch (IOException | ClassNotFoundException ex) {
            throw new InvalidSystemPath(this.classFilePath);
        }
    }

    private Class<?> loadClass(String classPath, String symbolicClassName) throws MalformedURLException, ClassNotFoundException {
        URL classPathURL = PathUtils.getURLFromClassPath(classPath);
        myLoader.addURL(classPathURL);
        return myLoader.loadClass(symbolicClassName);
    }
}
