package com.phonepe.cricketscoreboard.repository.impl;

import com.phonepe.cricketscoreboard.common.exception.CricketGameException;
import com.phonepe.cricketscoreboard.common.exception.ExceptionMessage;
import com.phonepe.cricketscoreboard.common.resquest.CreateGameRequest;
import com.phonepe.cricketscoreboard.repository.IGameDao;
import com.phonepe.cricketscoreboard.repository.entities.GameEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Repository
public class GameDao implements IGameDao {
    //Add this info in db
    private Map<String, GameEntity> games;

    public GameDao() {
        games = new HashMap<>();
    }

    @Override
    public String createGame(CreateGameRequest createGameRequest) {
        String gameId = UUID.randomUUID().toString();
        GameEntity gameEntity = GameEntity.builder()
                .numberOfTeamPlayers(createGameRequest.getNoOfPlayersInTeam())
                .overs(createGameRequest.getOvers())
                .playingTeam(createGameRequest.isPlayingTeam())
                .teamList(createGameRequest.getTeams())
                .build();
        games.put(gameId, gameEntity);
        return gameId;
    }

    @Override
    public GameEntity getGameById(String gameId) {
        validateGameId(gameId);
        GameEntity gameEntity = games.get(gameId);
        log.info("Game info:{}", gameEntity);
        return gameEntity;
    }

    private void validateGameId(String gameId) {
        if (!games.containsKey(gameId)) {
            log.error("Game Id does not exist:{}", gameId);
            throw new CricketGameException(ExceptionMessage.INVALID_GAME_ID);
        }
    }
}
