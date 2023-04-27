package ro.game.maps;

import lombok.extern.log4j.Log4j2;
import ro.game.explorations.Exploration;
import ro.players.Robot;
import ro.shared.Token;
import ro.utils.Movement;
import ro.utils.NodeUtils;

import java.util.List;

/**
 * The <tt>MatrixMap</tt> class implements all the matrix logic behind a {@link ro.game.explorations.MatrixExploration}
 */
@Log4j2
public class MatrixMap implements ExplorationMap {
    private final Cell[][] matrix;
    private final int length;

    public MatrixMap(int matrixLength) {
        this.length = matrixLength;
        this.matrix = new Cell[matrixLength][matrixLength];
        for (int i = 0; i < matrixLength; ++i) {
            for (int j = 0; j < matrixLength; ++j) {
                matrix[i][j] = new Cell();
            }
        }
    }

    @Override
    public List<Integer> getNeighbours(int nodeId) {
        Coordinates coordinates = NodeUtils.mapNodeToCoordinates(nodeId, length);
        return Movement.getValidNeighbours(coordinates, length);
    }

    @Override
    public void printFinalState() {
        for (int i = 0; i < length; ++i) {
            for (int j = 0; j < length; ++j) {
                System.out.printf("Cell (%d, %d) contains the following tokens: ", i, j);
                List<Token> tokenList = matrix[i][j].getTokenList();
                Exploration.printTokenList(tokenList);
            }
        }
    }

    @Override
    public void visit(Integer nodeId, Robot robot) {
        Coordinates coordinates = NodeUtils.mapNodeToCoordinates(nodeId, length);
        int row = coordinates.getRow(), col = coordinates.getColumn();
        synchronized (matrix[row][col]) {
            log.info(String.format("%s is visiting the cell (%d, %d)", robot, row, col));
            List<Token> extractedTokens = robot.extractTokensFromSharedData();
            if (extractedTokens.isEmpty()) {
                log.info(String.format("No tokens left for this cell, so %s stops execution", robot));
                robot.setRunning(false);
                return;
            }
            log.info(String.format("%s extracted the following tokens from the shared memory: %s", robot, extractedTokens));
            for (Token token : extractedTokens) {
                matrix[row][col].addToken(token);
            }
            log.info(String.format("%s finished to put the tokens in cell (%d, %d)", robot, row, col));
        }
    }
}
