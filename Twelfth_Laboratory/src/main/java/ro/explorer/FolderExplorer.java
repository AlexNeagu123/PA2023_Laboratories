package ro.explorer;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ro.exception.InvalidDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The <tt>FolderExplorer</tt> class recursively iterates through the directory tree starting from a given directory
 * {@code directoryPath} and finds all the ".class" files in this subtree.
 *
 * @author Alex Neagu
 */
@RequiredArgsConstructor
@Log4j2
public class FolderExplorer implements Explorer {
    private final String directoryPath;

    /**
     * @return A list of ".class" filenames thar have been found in the subtree
     */
    @Override
    public List<String> walk() throws InvalidDirectory {
        File file = new File(directoryPath);
        if (!file.isDirectory()) {
            throw new InvalidDirectory(directoryPath);
        }

        List<String> allClassFiles = new ArrayList<>();
        exploreFolder(file, allClassFiles);
        return allClassFiles;
    }

    private static void exploreFolder(File file, List<String> allClassFiles) {
        if (!file.isDirectory()) {
            if (file.getAbsolutePath().endsWith(".class")) {
                allClassFiles.add(file.getAbsolutePath());
            }
            return;
        }

        try {
            for (File childFile : Objects.requireNonNull(file.listFiles())) {
                exploreFolder(childFile, allClassFiles);
            }
        } catch (NullPointerException npe) {
            log.warn("Folder " + file.getAbsolutePath() + " does not have children");
        }
    }
}
