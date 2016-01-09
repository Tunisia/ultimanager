package com.clbarnes.ultimanager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by cbarnes on 1/8/16.
 */
public class TeamDb {
    private static final String TABLE_NAME = "teams";
    private static TeamDb instance = null;
    private Connection c = null;

    private TeamDb()
    {
        c = DatabaseConnection.getInstance().getConnection();
        ensureTableExists();
    }

    private void ensureTableExists()
    {
        Statement stmt;
        try {
            stmt = c.createStatement();
            String sql = String.format("CREATE TABLE IF NOT EXISTS %s ", TABLE_NAME) +
                    "(" +
                    "teamName TEXT PRIMARY KEY NOT NULL" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFromCsv(String csvPath)
    {
        File csvFile = new File(csvPath);

        try (CSVParser parser = CSVParser.parse(csvFile, StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader()))
        {
            for (CSVRecord row : parser.getRecords())
            {
                addTeam(new Team(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTeam(Team team)
    {
        Statement stmt;

        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO %s (teamName) ", TABLE_NAME) +
                    String.format("VALUES ('%s')", team.getTeamName());
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static TeamDb getInstance()
    {
        if (instance == null)
        {
            instance = new TeamDb();
        }

        return instance;
    }

    public Team getByTeamName(String teamName)
    {
        Statement stmt;
        Team team = null;

        try {
            stmt = c.createStatement();
            String query = String.format("SELECT * FROM %s ", TABLE_NAME) +
                    String.format("WHERE teamName = '%s' ", teamName) +
                    "LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);
            try
            {
                rs.next();
                team = new Team(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return team;
    }

    public ArrayList<Team> getAll()
    {
        Statement stmt;
        ArrayList<Team> teams = new ArrayList<>();

        try {
            stmt = c.createStatement();
            String query = String.format("SELECT * FROM %s ", TABLE_NAME);
            ResultSet rs = stmt.executeQuery(query);
            try
            {
                while (rs.next())
                {
                    teams.add(new Team(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teams;
    }
}
