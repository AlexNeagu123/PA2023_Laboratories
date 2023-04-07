package com.commands;

import com.entities.Catalog;
import com.exceptions.InvalidHTMLFile;
import com.utils.CatalogUtils;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * The <tt>ReportCommand</tt> class is responsible for creating an HTML Report generated using <tt>FreeMarker</tt>
 * for a given {@link Catalog} object.
 * <p>
 * The path of the HTML file where the report will be created should be specified.
 */
@AllArgsConstructor
@Log4j2
public class ReportCommand implements Command {
    private final Catalog catalog;
    private final String reportPath;
    private final Configuration cfg;

    /**
     * The HTML report is generated into the {@code reportPath}.
     * <p>
     * All the information extracted is copied into the {@code receiverCatalog} object.
     * <p>
     * Logs if the load couldn't take place.
     */
    @Override
    public void execute() {
        try {
            CatalogUtils.generateCatalogReport(catalog, reportPath, cfg);
        } catch (TemplateException | IOException templateException) {
            log.error("The report couldn't be generated.");
        } catch (InvalidHTMLFile openingException) {
            log.error(openingException.getMessage());
        }
    }
}
