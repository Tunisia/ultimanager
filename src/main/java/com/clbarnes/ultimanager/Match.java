package com.clbarnes.ultimanager;

import java.util.Random;

/**
 * Created by cbarnes on 1/8/16.
 */
public class Match {
    private Team[] teams;
    private static final int CAP = 17;

    public Match(Team team1, Team team2)
    {
        teams = new Team[] {team1, team2};
    }

    public MatchResult resolve()
    {
        Team winner;
        Team loser;

        if (getMeanAbility(teams[0]) > getMeanAbility(teams[1])){
            winner = teams[0];
            loser = teams[1];
        }
        else
        {
            winner = teams[1];
            loser = teams[0];
        }

        Random rand = new Random();

        int loserScore = rand.nextInt(CAP);
        int winnerScore = rand.nextInt(CAP + 1 - loserScore) + loserScore;

        return new MatchResult(winner, winnerScore, loser, loserScore);
    }

    private float getMeanAbility(Team team)
    {
        float count = 0;
        float abilitySum = 0;
        for (Player player : team.getPlayers())
        {
            count ++;
            abilitySum += player.getAbility();
        }

        return abilitySum / count;
    }

}
