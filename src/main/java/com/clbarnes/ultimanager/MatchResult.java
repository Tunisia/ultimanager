package com.clbarnes.ultimanager;

/**
 * Created by cbarnes on 1/8/16.
 */
public class MatchResult {
    private Team winner;
    private Team loser;
    private int winnerScore;
    private int loserScore;

    public MatchResult(Team winner, int winnerScore, Team loser, int loserScore)
    {
        setWinner(winner);
        setWinnerScore(winnerScore);
        setLoser(loser);
        setLoserScore(loserScore);
    }

    public String toString()
    {
        return String.format("%s %d - %d %s",
                getWinner().getTeamName(), getWinnerScore(),
                getLoserScore(), getLoser().getTeamName()
        );
    }


    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner) {
        this.winner = winner;
    }

    public Team getLoser() {
        return loser;
    }

    public void setLoser(Team loser) {
        this.loser = loser;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public int getLoserScore() {
        return loserScore;
    }

    public void setLoserScore(int loserScore) {
        this.loserScore = loserScore;
    }
}
