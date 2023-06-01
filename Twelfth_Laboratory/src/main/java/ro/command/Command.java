package ro.command;

import ro.exception.InvalidClass;
import ro.exception.InvalidDirectory;
import ro.exception.InvalidJar;
import ro.exception.InvalidSystemPath;

/**
 * The <tt>Command</tt> interface incorporates a specific command that the user can execute before
 * or after the exploration phase.
 */
public interface Command {
    void execute() throws InvalidClass, InvalidSystemPath, InvalidDirectory, InvalidJar;
}

