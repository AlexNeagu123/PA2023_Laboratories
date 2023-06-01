package ro.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.exception.InvalidJar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The <tt>JarExplorer</tt> class recursively iterates through a given <b>.jar</b> file
 * {@code jarPath} and finds all the ".class" files in this subtree.
 *
 * @author Alex Neagu
 */
@RequiredArgsConstructor
@Log4j2
public class JarExplorer implements Explorer {
    private final String jarPath;

    /**
     * @return A list of ".class" filenames thar have been found in the subtree
     */
    @Override
    public List<String> walk() throws InvalidJar {
        List<String> allClassFiles = new ArrayList<>();
        try (JarFile file = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    allClassFiles.add(entry.getName());
                }
            }
            return allClassFiles;
        } catch (IOException ex) {
            throw new InvalidJar(jarPath);
        }
    }
}
