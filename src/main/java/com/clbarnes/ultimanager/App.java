package com.clbarnes.ultimanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            Files.deleteIfExists(Paths.get("/home/cbarnes/code/ultimanager/db/data.db"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerDb playerDb = PlayerDb.getInstance();
        playerDb.addFromCsv("/home/cbarnes/code/ultimanager/db/players.csv");

        TeamDb teamDb = TeamDb.getInstance();
        teamDb.addFromCsv("/home/cbarnes/code/ultimanager/db/teams.csv");

        RoundRobin tournament = new RoundRobin(teamDb.getAll());
        String tournamentResults = tournament.resolve();

        System.out.println(tournamentResults);
    }
}
