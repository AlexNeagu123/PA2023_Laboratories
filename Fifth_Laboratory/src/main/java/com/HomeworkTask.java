package com;

import com.commands.*;
import com.entities.Catalog;
import com.entities.Document;
import com.utils.FreeMarkerUtils;
import freemarker.template.Configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeworkTask {
    public static void main(String[] args) throws IOException {
        // FreeMarker Configuration
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_0);
        FreeMarkerUtils.configureFreeMarker(cfg);

        // Info command test followed by a document report
        Catalog catalog = new Catalog("myCatalog");

        // Documents Creation
        Document doc1 = new Document("23dsw1", "CursJava", "C:\\Users\\alexneagu\\Faculty_Y2\\Second_Semester\\Advanced_Programming\\Courses\\Curs4\\streams.pdf");
        doc1.addTag("Profesor", "Cristian Frasinaru");
        Document doc2 = new Document("zldk12", "CursIP", "C:\\Users\\alexneagu\\Faculty_Y2\\Second_Semester\\Software_Engineering\\Courses\\IP05.pdf");
        doc2.addTag("Profesor", "Adrian Iftene");
        Document doc3 = new Document("wbns12", "StackOverflowQuestion", "https://stackoverflow.com/questions/75888095/understanding-of-kafka-auto-offset-reset");
        doc3.addTag("Year", "2023");

        // Commands Creation
        List<Command> commands = new ArrayList<>(Arrays.asList(
                new AddCommand(catalog, doc1),
                new AddCommand(catalog, doc2),
                new AddCommand(catalog, doc3),
                new ListCommand(catalog),
                new ViewCommand(doc1),
                new ViewCommand(doc2),
                new ViewCommand(doc3),
                new ReportCommand(catalog, "src/main/resources/report.html", cfg)
        ));

        // Commands Execution
        for (Command command : commands) {
            command.execute();
        }
    }
}
