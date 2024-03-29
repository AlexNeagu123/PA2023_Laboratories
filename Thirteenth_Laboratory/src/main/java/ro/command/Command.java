package ro.command;

import ro.shell.Shell;

/**
 * The <tt>Command</tt> interface incorporates a specific command that the user can execute before
 * or after the exploration phase.
 */
public interface Command {
    void execute(Shell shell);
}
