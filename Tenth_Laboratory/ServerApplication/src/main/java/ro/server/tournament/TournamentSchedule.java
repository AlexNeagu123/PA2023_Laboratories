package ro.server.tournament;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TournamentSchedule {
    private final List<List<Match>> dailyMatches;
    private final Set<Integer> players;

    public TournamentSchedule() {
        dailyMatches = new ArrayList<>();
        players = new HashSet<>();
    }

    public void addDay() {
        dailyMatches.add(new ArrayList<>());
    }

    public void addMatch(Match match) {
        dailyMatches.get(dailyMatches.size() - 1).add(match);
        players.add(match.getFirstPlayer());
        players.add(match.getSecondPlayer());
    }

    public int getDayCount() {
        return dailyMatches.size();
    }

    public List<Match> getMatchesOnDay(int day) {
        return dailyMatches.get(day);
    }

    public int getPlayerCount() {
        return players.size();
    }
}
