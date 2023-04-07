package com.commands;

import com.entities.Document;
import com.utils.CatalogUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * The <tt>ViewCommand</tt> class is responsible for opening a {@link Document} from a catalog using the native operating system
 * application.
 */
@AllArgsConstructor
@Log4j2
public class ViewCommand implements Command {
    private final Document document;

    /**
     * Opens the {@code document} using the native operating system application.
     * <p>
     * Logs if the opening couldn't take place.
     */
    @Override
    public void execute() {
        if (CatalogUtils.viewDocumentOnWeb(document.getLocation()))
            return;

        if (CatalogUtils.viewDocumentAsLocalFile(document.getLocation()))
            return;

        log.error("The document with id '" + document.getId() + "' couldn't be opened neither as web URI nor as local file");
    }
}

