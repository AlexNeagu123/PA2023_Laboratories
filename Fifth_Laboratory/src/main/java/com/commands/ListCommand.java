package com.commands;

import com.entities.Catalog;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * The <tt>ListCommand</tt> class is responsible for displaying information about all the documents contained in a {@link Catalog} object
 */
@AllArgsConstructor
@Log4j2
public class ListCommand implements Command {
    private final Catalog listedCatalog;

    @Override
    public void execute() {
        log.info(listedCatalog);
    }
}
