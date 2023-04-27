package ro.game.maps;

import lombok.extern.log4j.Log4j2;
import org.graph4j.Graph;
import org.graph4j.GraphBuilder;
import ro.game.explorations.Exploration;
import ro.players.Robot;
import ro.shared.Token;

import java.util.*;

/**
 * The <tt>GraphMap</tt> class implements all the graph logic behind a {@link ro.game.explorations.GraphExploration}
 */
@Log4j2
public class GraphMap implements ExplorationMap {
    private final Cell[] nodeTokens;
    private final int nodeCount;
    private final int edgeCount;
    private Graph graph;

    public GraphMap(int nodeCount, int edgeCount) {
        this.nodeCount = nodeCount;
        this.edgeCount = edgeCount;
        this.nodeTokens = new Cell[nodeCount];
        for (int nodeId = 0; nodeId < nodeCount; ++nodeId) {
            nodeTokens[nodeId] = new Cell();
        }
        initializeGraph();
    }

    void initializeGraph() {
        graph = GraphBuilder.numVertices(nodeCount).buildGraph();
        Set<Coordinates> freeEdges = new HashSet<>();
        for (int i = 0; i < nodeCount; ++i) {
            for (int j = i + 1; j < nodeCount; ++j) {
                freeEdges.add(new Coordinates(i, j));
            }
        }
        for (int i = 0; i < edgeCount; ++i) {
            Coordinates randomEdge = freeEdges.stream().skip(new Random().nextInt(freeEdges.size())).findFirst().orElse(null);
            if (randomEdge != null) {
                freeEdges.remove(randomEdge);
                graph.addEdge(randomEdge.getRow(), randomEdge.getColumn());
            }
        }
        System.out.println("The adjacency matrix of the generated graph looks like this");
        int[][] adjMatrix = graph.adjacencyMatrix();
        for (int i = 0; i < nodeCount; ++i) {
            for (int j = 0; j < nodeCount; ++j) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    @Override
    public List<Integer> getNeighbours(int nodeId) {
        int[] arr = graph.neighbors(nodeId);
        List<Integer> neighbours = new ArrayList<>();
        for (int it : arr) {
            neighbours.add(it);
        }
        return neighbours;
    }

    @Override
    public void printFinalState() {
        for (int i = 0; i < nodeCount; ++i) {
            System.out.printf("Node with id %d contains the following tokens: ", i);
            List<Token> tokenList = nodeTokens[i].getTokenList();
            Exploration.printTokenList(tokenList);
        }
    }

    @Override
    public void visit(Integer nodeId, Robot robot) {
        synchronized (nodeTokens[nodeId]) {
            log.info(String.format("%s is visiting the node %d", robot, nodeId));
            List<Token> extractedTokens = robot.extractTokensFromSharedData();
            if (extractedTokens.isEmpty()) {
                log.info(String.format("No tokens left for this node, so %s stops execution", robot));
                robot.setRunning(false);
                return;
            }
            log.info(String.format("%s extracted the following tokens from the shared memory: %s", robot, extractedTokens));
            for (Token token : extractedTokens) {
                nodeTokens[nodeId].addToken(token);
            }
            log.info(String.format("%s finished to put the tokens in node %d", robot, nodeId));
        }
    }
}

