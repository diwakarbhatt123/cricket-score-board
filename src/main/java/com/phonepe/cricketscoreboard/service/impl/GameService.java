package com.phonepe.cricketscoreboard.service.impl;

import com.phonepe.cricketscoreboard.common.response.CreateGameResponse;
import com.phonepe.cricketscoreboard.common.resquest.CreateGameRequest;
import com.phonepe.cricketscoreboard.repository.IGameDao;
import com.phonepe.cricketscoreboard.repository.IScoreBoardDao;
import com.phonepe.cricketscoreboard.service.IGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameService implements IGameService {

    private IGameDao gameDao;
    private IScoreBoardDao scoreBoardDao;

    public GameService(IGameDao gameDao, IScoreBoardDao scoreBoardDao) {
        this.gameDao = gameDao;
        this.scoreBoardDao = scoreBoardDao;
    }

    @Override
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) {
        String gameId = gameDao.createGame(createGameRequest);
        scoreBoardDao.createScoreBoard(gameId);
        return CreateGameResponse.builder()
                .gameId(gameId)
                .build();
    }
}
