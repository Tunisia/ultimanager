package com.clbarnes.ultimanager;

import org.apache.commons.csv.CSVRecord;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cbarnes on 1/8/16.
 */
public class Player {
    private String playerName;
    private int age;
    private String team;
    private int height;
    private float ability;

    public Player()
    {

    }

    public Player(CSVRecord record)
    {
        setPlayerName(record.get("playerName"));
        setAge(Integer.parseInt(record.get("age")));
        setTeam(record.get("team"));
        setHeight(Integer.parseInt(record.get("height")));
        setAbility(Float.parseFloat(record.get("ability")));
    }

    public Player(ResultSet rs)
    {
        try {
            setPlayerName(rs.getString("playerName"));
            setAge(rs.getInt("age"));
            setTeam(rs.getString("team"));
            setHeight(rs.getInt("height"));
            setAbility(rs.getFloat("ability"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public float getAbility() {
        return ability;
    }

    public void setAbility(float ability) {
        this.ability = ability;
    }
}
