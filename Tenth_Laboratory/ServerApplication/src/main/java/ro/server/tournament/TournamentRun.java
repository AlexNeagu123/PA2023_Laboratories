package ro.server.tournament;

import gurobi.GRBException;

public class TournamentRun {
    public static void main(String[] args) {
        try {
            TournamentScheduler tournamentScheduler = new TournamentScheduler(12, 2, 10);
            TournamentSchedule tournamentSchedule = tournamentScheduler.createSchedule();
            TournamentSimulation tournamentSimulation = new TournamentSimulation(tournamentSchedule);
            System.out.println("The winning sequence is");
            System.out.println(tournamentSimulation.getWinningSequence());
        } catch (GRBException grbException) {
            System.out.println("A correct schedule that respects the given parameters could not be generated." + grbException.getMessage());
        }
    }
}
