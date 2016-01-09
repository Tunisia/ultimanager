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
        setPlayers(getPlayersFromDb());
    }

    public Team(ResultSet rs)
    {
        try {
            setTeamName(rs.getString(TEAM_NAME_COL));
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
}
