package com;

import com.commands.AddCommand;
import com.commands.Command;
import com.commands.InfoCommand;
import com.commands.ReportCommand;
import com.entities.Catalog;
import com.entities.Document;
import com.generators.ColoringProblemGenerator;
import com.problem.GraphColoringProblem;
import com.problem.GreedyColoring;
import com.problem.JgraphTBrownColoring;
import com.problem.NodeColorPair;
import com.utils.FreeMarkerUtils;
import freemarker.template.Configuration;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class BonusTask {
    private static final Logger LOGGER = LogManager.getLogger(BonusTask.class);

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
                new InfoCommand(catalog),
                new ReportCommand(catalog, "src/main/resources/report.html", cfg)
        ));

        for (Command command : commands) {
            command.execute();
        }
        // Graph Coloring Algorithm Part
        GraphColoringProblem graphColoringProblem = ColoringProblemGenerator.generateColoringProblem(20);
        compareAlgorithms(graphColoringProblem);
    }

    public static void compareAlgorithms(GraphColoringProblem graphColoringProblem) {
        runJgraphTAlgorithm(graphColoringProblem);
        runGreedyAlgorithm(graphColoringProblem);
    }

    public static void runJgraphTAlgorithm(GraphColoringProblem graphColoringProblem) {
        JgraphTBrownColoring jgraphTBrownColoring = new JgraphTBrownColoring(graphColoringProblem);
        List<NodeColorPair> graphColoring = jgraphTBrownColoring.getColoring();
        log.info("The Brown Coloring Algorithm implemented by JgraphT generated a coloring with " + graphColoring.size() + " colors:");
        for (NodeColorPair pair : graphColoring) {
            log.info(pair);
        }
    }

    public static void runGreedyAlgorithm(GraphColoringProblem graphColoringProblem) {
        GreedyColoring greedyColoring = new GreedyColoring(graphColoringProblem);
        List<NodeColorPair> graphColoring = greedyColoring.getColoring();
        log.info("The Greedy Coloring Algorithm implemented by me generated a coloring with " + graphColoring.size() + " colors:");
        for (NodeColorPair pair : graphColoring) {
            log.info(pair);
        }
    }
}
