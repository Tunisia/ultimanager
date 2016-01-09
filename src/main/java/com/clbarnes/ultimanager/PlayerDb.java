package com.clbarnes.ultimanager;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.csv.CSVRecord;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by cbarnes on 1/8/16.
 */
public class PlayerDb {
    private static final String TABLE_NAME = "players";
    private static PlayerDb instance = null;
    private Connection c = null;

    private PlayerDb()
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
                    "playerName TEXT PRIMARY KEY NOT NULL," +
                    "age INT," +
                    "team TEXT," +
                    "height INT," +
                    "ability FLOAT" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PlayerDb getInstance()
    {
        if (instance == null)
        {
            instance = new PlayerDb();
        }

        return instance;
    }

    public ArrayList<Player> getByCurrentTeam(String teamName)
    {
        Statement stmt;
        ArrayList<Player> players = new ArrayList<>();

        try {
            stmt = c.createStatement();
            String query = String.format("SELECT * FROM %s ", TABLE_NAME) +
                    String.format("WHERE team = '%s' ", teamName);
            ResultSet rs = stmt.executeQuery(query);
            try
            {
                while (rs.next())
                {
                    players.add(new Player(rs));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return players;
    }


    public Player getByName(String playerName)
    {
        Statement stmt;
        Player player = null;

        try {
            stmt = c.createStatement();
            String query = String.format("SELECT * FROM %s ", TABLE_NAME) +
                    String.format("WHERE playerName = %s ", playerName) +
                    "LIMIT 1";
            ResultSet rs = stmt.executeQuery(query);
            try
            {
                rs.next();
                player = new Player(rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return player;
    }

    public void addFromCsv(String csvPath)
    {
        File csvFile = new File(csvPath);

        try (CSVParser parser = CSVParser.parse(csvFile, StandardCharsets.UTF_8, CSVFormat.EXCEL.withHeader()))
        {
            for (CSVRecord row : parser.getRecords())
            {
                addPlayer(new Player(row));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(Player player)
    {
        Statement stmt;

        try {
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = String.format("INSERT INTO %s (playerName, age, team, height, ability) ", TABLE_NAME) +
                    String.format(
                            "VALUES ('%s', %d, '%s', %d, %f)",
                            player.getPlayerName(),
                            player.getAge(),
                            player.getTeam(),
                            player.getHeight(),
                            player.getAbility()
                    );
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
