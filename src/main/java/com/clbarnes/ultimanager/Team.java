package com.clbarnes.ultimanager;

import org.apache.commons.csv.CSVRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by cbarnes on 1/8/16.
 */
public class Team {
    private String teamName;
    private static final String TEAM_NAME_COL = "teamName";
    private int wins;
    private static final String WINS_COL = "wins";
    private int losses;
    private static final String LOSSES_COL = "losses";
    private ArrayList<Player> players;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private Team()
    {

    }

    public Team(CSVRecord record)
    {
        setTeamName(record.get("teamName"));
        setWins(Integer.parseInt(record.get("wins")));
        setLosses(Integer.parseInt(record.get("losses")));
        setPlayers(getPlayersFromDb());
    }

    public Team(ResultSet rs)
    {
        try {
            setTeamName(rs.getString(TEAM_NAME_COL));
            setWins(rs.getInt(WINS_COL));
            setLosses(rs.getInt(LOSSES_COL));
            setPlayers(getPlayersFromDb());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Player> getPlayersFromDb()
    {
        return PlayerDb.getInstance().getByCurrentTeam(getTeamName());
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getGamesPlayed()
    {
        return this.wins + this.losses;
    }
}
