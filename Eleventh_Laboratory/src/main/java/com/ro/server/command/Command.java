package com.ro.server.command;

import com.ro.server.exception.GameException;

/**
 * The <tt>Command</tt> interface incorporates a specific command that the user can execute before
 * or after the exploration phase.
 */

public interface Command {
    void execute() throws GameException;
}

