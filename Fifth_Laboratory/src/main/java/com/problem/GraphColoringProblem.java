package com.problem;

import com.entities.Catalog;
import com.entities.Document;
import com.utils.MetadataUtils;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * The <tt>GraphColoringProblem</tt> class has the role of storing all the relevant information about an instance of
 * the graph coloring problem.</tt>.
 * <p>
 * This class stores an undirected graph where a node is characterized by a {@link Document} object.
 * <p>
 * An edge between two {@link Document} objects exists if there are common tags or metadata between them.
 */
@ToString
@Log4j2
public class GraphColoringProblem {
    private final Map<Document, Set<Document>> neighbours;
    private final int nodeCount;
    private int edgeCount;

    /**
     * @param catalog A {@link Catalog} object that contains multiple documents. The graph is constructed based on the
     *                {@link Document} objects contained in this catalog
     */
    public GraphColoringProblem(Catalog catalog) {
        this.nodeCount = catalog.documentCount();
        this.edgeCount = 0;
        neighbours = new HashMap<>();
        for (Document firstDoc : catalog.getDocuments()) {
            for (Document secondDoc : catalog.getDocuments()) {
                if (firstDoc.equals(secondDoc)) {
                    continue;
                }
                if (MetadataUtils.existsCommonTag(firstDoc.getTags(), secondDoc.getTags())
                        || MetadataUtils.existsCommonTag(firstDoc.getCachedMetadata(), secondDoc.getCachedMetadata())) {
                    addEdge(firstDoc, secondDoc);
                }
            }
        }
        log.info("Created a graph coloring problem instance with " + nodeCount + " nodes and " + edgeCount + " edges");
    }

    /**
     * @param doc A {@link Document} object
     * @return A list of all the neighbours of the {@code doc} node
     */
    public List<Document> getNeighboursOfNode(Document doc) {
        if (neighbours.get(doc) == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(neighbours.get(doc));
    }

    public List<Document> getAllNodes() {
        return new ArrayList<>(neighbours.keySet());
    }

    private void addEdge(Document firstDoc, Document secondDoc) {
        this.edgeCount++;
        neighbours.computeIfAbsent(firstDoc, k -> new HashSet<>());
        neighbours.computeIfAbsent(secondDoc, k -> new HashSet<>());
        neighbours.get(firstDoc).add(secondDoc);
        neighbours.get(secondDoc).add(firstDoc);
    }
}
