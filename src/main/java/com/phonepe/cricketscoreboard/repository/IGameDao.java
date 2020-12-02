package com.phonepe.cricketscoreboard.repository;

import com.phonepe.cricketscoreboard.common.resquest.CreateGameRequest;
import com.phonepe.cricketscoreboard.repository.entities.GameEntity;

public interface IGameDao {

    public String createGame(CreateGameRequest createGameRequest);

    public GameEntity getGameById(String gameId);
}
