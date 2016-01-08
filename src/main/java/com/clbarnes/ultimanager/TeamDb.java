package com.clbarnes.ultimanager;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.sql.Connection;
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
        throw new NotImplementedException();
    }

    public ArrayList<Team> getAll()
    {
        throw new NotImplementedException();
    }
}
