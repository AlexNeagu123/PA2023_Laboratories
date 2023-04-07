package com;

import com.commands.*;
import com.entities.Catalog;
import com.entities.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompulsoryTask {
    public static void main(String[] args) {
        Catalog catalog = new Catalog("savedCatalog");
        Catalog loadedCatalog = new Catalog();

        // Documents Creation
        Document doc = new Document("23dsw1", "CursJava", "C:\\Users\\alexneagu\\Faculty_Y2\\Second_Semester\\Advanced_Programming\\Courses\\Curs4\\streams.pdf");
        doc.addTag("Profesor", "Cristian Frasinaru");

        // Commands Creation
        List<Command> commands = new ArrayList<>(Arrays.asList(
                new AddCommand(catalog, doc),
                new SaveCommand(catalog, "src/main/resources/testSave.json"),
                new LoadCommand(loadedCatalog, "src/main/resources/testLoad.json")
        ));

        // Execute commands
        for (Command command : commands) {
            command.execute();
        }

        Command viewCommand = new ListCommand(loadedCatalog);
        viewCommand.execute();
    }
}
