package ro.server.tournament;

import lombok.extern.log4j.Log4j2;
import org.graph4j.Digraph;
import org.graph4j.GraphBuilder;
import org.graph4j.alg.Tournament;

import java.util.Arrays;
import java.util.List;

/**
 * This class is responsible for generating random outcomes for all games scheduled in a Tournament.
 * The games are taken from an already existing {@link TournamentSchedule}
 * <p>
 * Also, after the outcomes are generated, this class provides functionality for finding a permutation _p_ of all players such that
 * p[i] beats p[i + 1] for all 1 <= i <= n - 1
 *
 * @author Alex Neagu
 */
@Log4j2
public class TournamentSimulation {
    private final TournamentSchedule tournamentSchedule;
    private final Digraph digraph;

    public TournamentSimulation(TournamentSchedule tournamentSchedule) {
        this.tournamentSchedule = tournamentSchedule;
        int numVertices = tournamentSchedule.getPlayerCount();
        this.digraph = GraphBuilder.numVertices(numVertices).estimatedNumEdges((long) numVertices * numVertices).buildDigraph();
        generateRandomOutcomes();
    }

    private void generateRandomOutcomes() {
        log.info("The tournament finished and here are the results for each match: ");
        for (int day = 0; day < tournamentSchedule.getDayCount(); ++day) {
            log.info("Day " + day + ".");
            for (Match match : tournamentSchedule.getMatchesOnDay(day)) {
                log.info("Player " + match.getFirstPlayer() + " vs " + match.getSecondPlayer());
                int randomWinner = (int) Math.round(Math.random());
                if (randomWinner == 0) {
                    log.info("Player " + match.getFirstPlayer() + " won the match");
                    digraph.addEdge(match.getFirstPlayer(), match.getSecondPlayer());
                } else {
                    log.info("Player " + match.getSecondPlayer() + " won the match");
                    digraph.addEdge(match.getSecondPlayer(), match.getFirstPlayer());
                }
            }
        }
    }

    public List<Integer> getWinningSequence() {
        Tournament tournament = new Tournament(digraph);
        return Arrays.stream(tournament.getHamiltonianPath().vertices()).boxed().toList();
    }
}
