package com.example.player.repository;

import com.example.player.model.Player;

import java.util.*;

public interface PlayerRepository {
    ArrayList<Player> getPlayers();

    Player addPlayer(Player players);

    Player getPlayerById(int playerId);

    Player updatePlayerById(int playerId, Player players);

    void deletePlayer(int playerId);

}