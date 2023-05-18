package ro.server.tournament;

import gurobi.*;
import lombok.RequiredArgsConstructor;

/**
 * This class is responsible for creating a tournament schedule that respects the following criteria
 * <p>
 * - There are <tt>n</tt> players in the Tournament
 * <p>
 * - Each player will play with every other player exactly once
 * <p>
 * - A player can not have more than <tt>p</tt> games in a day
 * <p>
 * - The tournament must finish in at most <tt>d</tt> days
 *
 * <p>
 * <b>Gurobi</b> library is used in order to solve this Integer Linear Programming problem.
 *
 * @author Alex Neagu
 * @see <a href="https://en.wikipedia.org/wiki/Integer_programming">Integer Linear Programmin (ILP) </a>
 */
@RequiredArgsConstructor
public class TournamentScheduler {
    private final int playerCount;
    private final int maxGamesPerDay;
    private final int maxTournamentDays;

    public TournamentSchedule createSchedule() throws GRBException {
        GRBEnv env = new GRBEnv();
        env.set(GRB.IntParam.LogToConsole, 0);
        GRBModel model = new GRBModel(env);

        GRBVar[][][] vars = new GRBVar[playerCount][playerCount][maxTournamentDays];
        for (int firstPlayer = 0; firstPlayer < playerCount; ++firstPlayer) {
            for (int secondPlayer = 0; secondPlayer < playerCount; ++secondPlayer) {
                if (firstPlayer == secondPlayer) {
                    continue;
                }
                for (int day = 0; day < maxTournamentDays; ++day) {
                    vars[firstPlayer][secondPlayer][day] = model.addVar(0.0, 1.0, 0.0, GRB.BINARY, "x_" + firstPlayer + "_" + secondPlayer + "_" + day);
                }
            }
        }

        setSymmetryConstraint(model, vars);
        setPlayOnceConstraint(model, vars);
        setLimitPerDayConstraint(model, vars);
        model.optimize();

        return generateTournamentSchedule(vars);
    }

    private TournamentSchedule generateTournamentSchedule(GRBVar[][][] vars) throws GRBException {
        TournamentSchedule tournamentSchedule = new TournamentSchedule();
        for (int day = 0; day < maxTournamentDays; ++day) {
            tournamentSchedule.addDay();
            for (int firstPlayer = 0; firstPlayer < playerCount; ++firstPlayer) {
                for (int secondPlayer = firstPlayer + 1; secondPlayer < playerCount; ++secondPlayer) {
                    if (vars[firstPlayer][secondPlayer][day].get(GRB.DoubleAttr.X) == 1.0) {
                        tournamentSchedule.addMatch(new Match(firstPlayer, secondPlayer));
                    }
                }
            }
        }
        return tournamentSchedule;
    }

    private void setSymmetryConstraint(GRBModel model, GRBVar[][][] vars) throws GRBException {
        for (int firstPlayer = 0; firstPlayer < playerCount; ++firstPlayer) {
            for (int secondPlayer = firstPlayer + 1; secondPlayer < playerCount; ++secondPlayer) {
                for (int day = 0; day < maxTournamentDays; ++day) {
                    GRBLinExpr leftExpr = new GRBLinExpr();
                    leftExpr.addTerm(1.0, vars[firstPlayer][secondPlayer][day]);
                    GRBLinExpr rightExpr = new GRBLinExpr();
                    rightExpr.addTerm(1.0, vars[secondPlayer][firstPlayer][day]);
                    model.addConstr(leftExpr, GRB.EQUAL, rightExpr, "symmetry_" + firstPlayer + "_" + secondPlayer + "_" + day);
                }
            }
        }
    }

    private void setPlayOnceConstraint(GRBModel model, GRBVar[][][] vars) throws GRBException {
        for (int firstPlayer = 0; firstPlayer < playerCount; ++firstPlayer) {
            for (int secondPlayer = 0; secondPlayer < playerCount; ++secondPlayer) {
                if (firstPlayer == secondPlayer) {
                    continue;
                }
                GRBLinExpr expr = new GRBLinExpr();
                for (int day = 0; day < maxTournamentDays; ++day) {
                    expr.addTerm(1.0, vars[firstPlayer][secondPlayer][day]);
                }
                model.addConstr(expr, GRB.EQUAL, 1.0, "play_once_" + firstPlayer + "_" + secondPlayer);
            }
        }
    }

    private void setLimitPerDayConstraint(GRBModel model, GRBVar[][][] vars) throws GRBException {
        for (int firstPlayer = 0; firstPlayer < playerCount; ++firstPlayer) {
            for (int day = 0; day < maxTournamentDays; ++day) {
                GRBLinExpr expr = new GRBLinExpr();
                for (int secondPlayer = 0; secondPlayer < playerCount; ++secondPlayer) {
                    if (firstPlayer == secondPlayer) {
                        continue;
                    }
                    expr.addTerm(1.0, vars[firstPlayer][secondPlayer][day]);
                }
                model.addConstr(expr, GRB.LESS_EQUAL, maxGamesPerDay, "max_games_day_" + firstPlayer + "_" + day);
            }
        }
    }
}
