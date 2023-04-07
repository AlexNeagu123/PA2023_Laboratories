package com.utils;

import com.entities.Catalog;
import com.entities.Document;
import com.exceptions.InvalidHTMLFile;
import com.exceptions.UnrecognizedPathException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The <tt>CatalogUtils</tt> class has several methods that are called in the {@link com.commands.Command} derived objects.
 */

public class CatalogUtils {
    /**
     * @param catalog A {@link Catalog} object to be saved
     * @param path    The path of the json path where the object will be stored
     * @throws IOException If the saving couldn't take place
     */
    public static void save(Catalog catalog, String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new File(path), catalog);
    }

    /**
     * @param path The path of the json path where the object is located
     * @return A {@link Catalog} object that is loaded from the file
     * @throws IOException If the saving couldn't take place
     */
    public static Catalog load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(path), Catalog.class);
    }

    /**
     * Opens a document located at a specified path in the browser
     *
     * @param uriPath The uri path of the document
     * @return <tt>true</tt> if the document was successfully opened, <tt>false</tt> otherwise
     */
    public static boolean viewDocumentOnWeb(String uriPath) {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI documentURI = new URI(uriPath);
            desktop.browse(documentURI);
            return true;
        } catch (URISyntaxException | IOException uriException) {
            return false;
        }
    }

    /**
     * Opens a document located at a specified path in the local file system
     *
     * @param filePath The absolute path of the document
     * @return <tt>true</tt> if the document was successfully opened, <tt>false</tt> otherwise
     */
    public static boolean viewDocumentAsLocalFile(String filePath) {
        try {
            Desktop desktop = Desktop.getDesktop();
            File documentFile = new File(filePath);
            desktop.open(documentFile);
            return true;
        } catch (IOException | IllegalArgumentException pathException) {
            return false;
        }
    }

    /**
     * Generates a html report for a specified catalog and opens it in the browser.
     *
     * @param catalog    The {@link Catalog} object from the html report
     * @param reportPath The path where the HTML report will be stored
     * @param cfg        The configuration for FreeMarker libary
     * @throws IOException       If the {@code reportPath} is not valid
     * @throws TemplateException If there was an error in generating the HTML Report
     * @throws InvalidHTMLFile   If there was an error in opening the HTML report in the browser
     */
    public static void generateCatalogReport(Catalog catalog, String reportPath, Configuration cfg) throws IOException, TemplateException, InvalidHTMLFile {
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("catalog", catalog);
        dataModel.put("title", "catalogReport");

        Template template = cfg.getTemplate("template.ftl");
        Writer fileWriter = new BufferedWriter(new FileWriter(reportPath));
        template.process(dataModel, fileWriter);
        fileWriter.close();

        if (viewDocumentAsLocalFile(reportPath))
            return;

        throw new InvalidHTMLFile(reportPath);
    }

    /**
     * @param filePath The absolute path of a local file
     * @return An {@link InputStream} object corresponding to the {@code filePath} or <tt>null</tt> if the path is invalid
     */
    private static InputStream getLocalFileStream(String filePath) {
        try {
            return Files.newInputStream(Paths.get(filePath));
        } catch (IOException | InvalidPathException fileException) {
            return null;
        }
    }

    /**
     * @param urlPath The url path of a web document
     * @return An {@link InputStream} object corresponding to the {@code urlPath} or <tt>null</tt> if the path is invalid
     */
    private static InputStream getURLStream(String urlPath) {
        try {
            URL url = new URL(urlPath);
            return url.openStream();
        } catch (IOException urlException) {
            return null;
        }
    }

    /**
     * Extracts metadata from a document and adds it to its {@code cachedMetadata} list
     *
     * @param document The document
     * @throws IOException               If the location of the document is invalid
     * @throws TikaException             If there was a problem in generating the metadata
     * @throws SAXException              If there was a problem in generating the metadata
     * @throws UnrecognizedPathException If the location of the document is invalid
     */
    public static void printDocumentMetadata(Document document) throws IOException, TikaException, SAXException, UnrecognizedPathException {
        AutoDetectParser parser = new AutoDetectParser();
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        ParseContext parseContext = new ParseContext();
        String docLocation = document.getLocation();

        InputStream stream = getLocalFileStream(docLocation);
        if (stream == null) {
            stream = getURLStream(docLocation);
        }
        if (stream == null) {
            throw new UnrecognizedPathException(docLocation);
        }

        parser.parse(stream, handler, metadata, parseContext);
        stream.close();

        for (String name : metadata.names()) {
            document.addMetadata(name, metadata.get(name));
        }
    }
}
