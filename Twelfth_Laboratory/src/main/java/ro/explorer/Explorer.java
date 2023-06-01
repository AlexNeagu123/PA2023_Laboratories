package ro.explorer;

import ro.exception.InvalidDirectory;
import ro.exception.InvalidJar;

import java.io.IOException;
import java.util.List;

public interface Explorer {
    List<String> walk() throws InvalidDirectory, IOException, InvalidJar;
}
