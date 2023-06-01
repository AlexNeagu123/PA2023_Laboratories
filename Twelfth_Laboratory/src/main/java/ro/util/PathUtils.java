package ro.util;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class contains useful static methods for determining the package name, symbolic class name, class path and URL associated to a
 * given class. Only the absolute path of the ".class" file should be passed as an argument
 */
public class PathUtils {
    public static String getPackageNameFromAbsolutePath(String absolutePath) throws IOException {
        ClassParser classParser = new ClassParser(absolutePath);
        JavaClass javaClass = classParser.parse();
        return javaClass.getPackageName();
    }

    public static String getSymbolicClassNameFromAbsolutePath(String absolutePath, String packageName) {
        int packageIndex = absolutePath.indexOf(packageName.replace('.', '\\'));
        return absolutePath.substring(packageIndex).replace('\\', '.').replace(".class", "");
    }

    public static String getClassPathFromAbsolutPath(String absolutePath, String symbolicClassName) {
        int classNameIndex = absolutePath.indexOf(symbolicClassName.replace('.', '\\'));
        return absolutePath.substring(0, classNameIndex);
    }

    public static URL getURLFromClassPath(String classPath) throws MalformedURLException {
        File path = new File(classPath);
        return path.toURI().toURL();
    }

}
