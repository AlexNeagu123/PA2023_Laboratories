package com.ro.api.vsp;

import com.ro.api.dto.response.GameResponseDto;
import com.ro.api.dto.response.PlayerResponseDto;
import com.ro.api.exceptions.OptimizationException;
import gurobi.*;

import java.util.*;

public class VertexSeparator {
    private final List<PlayerResponseDto> players;
    private final List<GameResponseDto> games;
    private int playerCount;
    private Map<Long, Integer> playerIdCompression;
    private final int groupSizeLimit;

    public VertexSeparator(List<GameResponseDto> games, List<PlayerResponseDto> players) {
        this.games = games;
        this.players = players;
        compressIds();
        this.groupSizeLimit = 2 * this.playerCount / 3;
    }

    public List<PlayerResponseDto> computeVertexSeparator() {
        try {
            GRBEnv env = new GRBEnv();
            env.set(GRB.IntParam.LogToConsole, 0);
            GRBModel model = new GRBModel(env);

            GRBVar[] firstGroupVars = new GRBVar[this.playerCount];
            GRBVar[] secondGroupVars = new GRBVar[this.playerCount];

            for (int playerInd = 0; playerInd < this.playerCount; playerInd++) {
                firstGroupVars[playerInd] = model.addVar(0.0, 1.0, 1.0, GRB.BINARY, "fg_" + playerInd);
                secondGroupVars[playerInd] = model.addVar(0.0, 1.0, 1.0, GRB.BINARY, "sg_" + playerInd);
            }
            
            setMaximizationConstraint(model, firstGroupVars, secondGroupVars);
            setDisjointConstraint(model, firstGroupVars, secondGroupVars);
            setUniqueConstraint(model, firstGroupVars, secondGroupVars);
            setGroupSizeConstraint(model, firstGroupVars, secondGroupVars);

            model.optimize();

            List<PlayerResponseDto> vertexSeparator = new ArrayList<>();
            for (PlayerResponseDto player : this.players) {
                int compressedIndex = this.playerIdCompression.get(player.getId());
                if (firstGroupVars[compressedIndex].get(GRB.DoubleAttr.X) == 0.0 && secondGroupVars[compressedIndex].get(GRB.DoubleAttr.X) == 0.0) {
                    vertexSeparator.add(player);
                }
            }
            return vertexSeparator;
        } catch (GRBException ex) {
            throw new OptimizationException(ex.getMessage());
        }
    }

    private void setGroupSizeConstraint(GRBModel model, GRBVar[] firstGroupVars, GRBVar[] secondGroupVars) throws GRBException {
        GRBLinExpr firstGroupSumExpr = new GRBLinExpr();
        GRBLinExpr secondGroupSumExpr = new GRBLinExpr();

        for (int playerInd = 0; playerInd < this.playerCount; playerInd++) {
            firstGroupSumExpr.addTerm(1.0, firstGroupVars[playerInd]);
            secondGroupSumExpr.addTerm(1.0, secondGroupVars[playerInd]);
        }

        model.addConstr(firstGroupSumExpr, GRB.GREATER_EQUAL, 1.0, "fg_sum >= 1");
        model.addConstr(secondGroupSumExpr, GRB.GREATER_EQUAL, 1.0, "sg_sum >= 1");

        model.addConstr(firstGroupSumExpr, GRB.LESS_EQUAL, this.groupSizeLimit - 1, "fg_sum < 2 * n / 3");
        model.addConstr(secondGroupSumExpr, GRB.LESS_EQUAL, this.groupSizeLimit - 1, "sg_sum < 2 * n / 3");
    }

    private void setUniqueConstraint(GRBModel model, GRBVar[] firstGroupVars, GRBVar[] secondGroupVars) throws GRBException {
        for (int playerInd = 0; playerInd < this.playerCount; playerInd++) {
            GRBLinExpr leftExpr = new GRBLinExpr();
            leftExpr.addTerm(1.0, firstGroupVars[playerInd]);
            leftExpr.addTerm(1.0, secondGroupVars[playerInd]);
            model.addConstr(leftExpr, GRB.LESS_EQUAL, 1.0, "unique_constr_" + playerInd);
        }
    }

    private void setDisjointConstraint(GRBModel model, GRBVar[] firstGroupVars, GRBVar[] secondGroupVars) throws GRBException {
        for (GameResponseDto game : games) {
            int from = playerIdCompression.get(game.getFirstPlayer().getId());
            int to = playerIdCompression.get(game.getSecondPlayer().getId());

            for (int step = 0; step < 2; ++step) {
                GRBLinExpr leftExpr = new GRBLinExpr();
                leftExpr.addTerm(1.0, firstGroupVars[from]);
                leftExpr.addTerm(1.0, secondGroupVars[to]);
                model.addConstr(leftExpr, GRB.LESS_EQUAL, 1.0, "disjoint_constr_" + from + "_" + to);
                // swap from and to
                int aux = from;
                from = to;
                to = aux;
            }
        }
    }

    private void setMaximizationConstraint(GRBModel model, GRBVar[] firstGroupVars, GRBVar[] secondGroupVars) throws GRBException {
        GRBLinExpr sumExpr = new GRBLinExpr();
        for (int playerInd = 0; playerInd < this.playerCount; playerInd++) {
            sumExpr.addTerm(1.0, firstGroupVars[playerInd]);
            sumExpr.addTerm(1.0, secondGroupVars[playerInd]);
        }
        model.setObjective(sumExpr, GRB.MAXIMIZE);
    }

    private void compressIds() {
        this.playerIdCompression = new HashMap<>();
        this.playerCount = 0;
        for (PlayerResponseDto player : this.players) {
            this.playerIdCompression.put(player.getId(), this.playerCount);
            this.playerCount++;
        }
    }
}
