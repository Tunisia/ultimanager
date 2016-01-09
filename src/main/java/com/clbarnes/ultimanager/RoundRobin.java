package com.clbarnes.ultimanager;

import java.util.*;

/**
 * Created by cbarnes on 1/8/16.
 */
public class RoundRobin {
    private ArrayList<Team> teams;

    public RoundRobin(ArrayList<Team> teams)
    {
        this.teams = teams;
    }

    public String resolve()
    {
        return placingToString(determinePlacing(resolveSchedule(getSchedule())));
    }

    public ArrayList<Match> getSchedule()
    {
        ArrayList<Match> matches = new ArrayList<>();

        for (int i = 0 ; i < teams.size() - 1 ; i++)
        {
            Team team1 = teams.get(i);
            for (int j = i ; j < teams.size() ; j++)
            {
                Team team2 = teams.get(j);
                matches.add(new Match(team1, team2));
            }
        }

        return matches;
    }

    public ArrayList<MatchResult> resolveSchedule(ArrayList<Match> matches)
    {
        ArrayList<MatchResult> results = new ArrayList<>();
        for (Match match : matches)
        {
            MatchResult result = match.resolve();
            results.add(result);
        }
        return results;
    }

    public ArrayList<TournamentPerformance> determinePlacing(ArrayList<MatchResult> results) {
        HashMap<Team, TournamentPerformance> performances = new HashMap<>();
        for (Team team : teams) {
            performances.put(team, new TournamentPerformance(team));
        }

        for (MatchResult result : results) {
            performances.get(result.getWinner()).incrementWins();

            performances.get(result.getWinner()).incrementPointsScored(result.getWinnerScore());
            performances.get(result.getLoser()).incrementPointsScored(result.getLoserScore());

            int pointsDiff = result.getWinnerScore() - result.getLoserScore();
            performances.get(result.getWinner()).incrementPointsDiff(pointsDiff);
            performances.get(result.getLoser()).incrementPointsDiff(-pointsDiff);
        }

        ArrayList<TournamentPerformance> performanceList = new ArrayList<>(performances.values());
        Collections.sort(performanceList, new PerformanceComparator());

        return performanceList;
    }

    public String placingToString(ArrayList<TournamentPerformance> performanceList)
    {
        StringBuilder strbuild = new StringBuilder();
        int count = 1;
        for (TournamentPerformance performance : performanceList)
        {
            strbuild.append(String.format("%d. %s\n", count, performance.toString()));
            count ++;
        }

        return strbuild.toString();
    }

    static class PerformanceComparator implements Comparator<TournamentPerformance>
    {

        @Override
        public int compare(TournamentPerformance o1, TournamentPerformance o2)
        {
            int diff = o2.getWins() - o1.getWins();
            if (diff == 0)
            {
                diff = o2.getPointsDiff() - o1.getPointsDiff();
                if (diff == 0)
                {
                    diff = o2.getPointsScored() - o1.getPointsScored();
                    if (diff == 0)
                    {
                        Random rand = new Random();
                        diff = rand.nextInt(2)*2 - 1;
                    }
                }
            }
            return diff;
        }
    }

    private class TournamentPerformance
    {
        private Team team;
        private int wins = 0;
        private int pointsDiff = 0;
        private int pointsScored = 0;

        protected TournamentPerformance(Team team)
        {
            this.team = team;
        }

        public String toString()
        {
            return String.format("%s with %d wins, a PD of %d, and %d points scored.",
                    this.team.getTeamName(), getWins(), getPointsDiff(), getPointsScored()
            );
        }

        protected void incrementWins()
        {
            this.wins += 1;
        }

        protected int getWins()
        {
            return this.wins;
        }

        protected void incrementPointsDiff(int pointsDiff)
        {
            this.pointsDiff += pointsDiff;
        }

        protected int getPointsDiff()
        {
            return this.pointsDiff;
        }

        protected void incrementPointsScored(int pointsScored)
        {
            this.pointsScored += pointsScored;
        }

        protected int getPointsScored()
        {
            return this.pointsScored;
        }

    }
}
