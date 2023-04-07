package com.commands;

/**
 * The <tt>Command</tt> interface represents a specific operation that can be done involving {@link com.entities.Document}
 * and {@link com.entities.Catalog} objects.
 * <p>
 * In other words, this interface is used for implementing the <b>Command Design Patter</b>
 */
public interface Command {
    /**
     * Executes the command
     */
    void execute();
}
