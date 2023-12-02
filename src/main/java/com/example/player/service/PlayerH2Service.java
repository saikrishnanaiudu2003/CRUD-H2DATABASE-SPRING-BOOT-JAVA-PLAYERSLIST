package com.example.player.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.*;
import com.example.player.repository.PlayerRepository;
import com.example.player.model.Player;
import com.example.player.model.PlayerRowMapper;

@Service
public class PlayerH2Service implements PlayerRepository {
    @Autowired
    private JdbcTemplate db;

    @Override
    public ArrayList<Player> getPlayers() {
        return (ArrayList<Player>) db.query("select * from team", new PlayerRowMapper());
    }

    @Override
    public Player addPlayer(Player players) {
        db.update("insert into team(playerName,jerseyNumber,role)values(?,?,?)", players.getPlayerName(),
                players.getJerseyNumber(), players.getRole());
        Player savedPlayer = db.queryForObject("select * from team where playerName=? and jerseyNumber=? and role=?",
                new PlayerRowMapper(), players.getPlayerName(), players.getJerseyNumber(),
                players.getRole());
        return savedPlayer;
    }

    @Override
    public Player getPlayerById(int playerId) {
        try {
            Player player = db.queryForObject("select * from team where playerId=?", new PlayerRowMapper(), playerId);
            return player;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Player updatePlayerById(int playerId, Player players) {
        if (players.getPlayerName() != null) {
            db.update("update team set playerName=? where playerId=?", players.getPlayerName(), playerId);
        }
        if (players.getJerseyNumber() != 0) {
            db.update("update team set jerseyNumber=? where playerId=?", players.getJerseyNumber(), playerId);
        }
        if (players.getRole() != null) {
            db.update("update team set role=? where playerId=?", players.getRole(), playerId);
        }
        return getPlayerById(playerId);
    }

    @Override
    public void deletePlayer(int playerId) {
        db.update("delete from team where playerId=?", playerId);
    }

}