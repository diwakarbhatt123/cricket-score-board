package com.phonepe.cricketscoreboard.service;

import com.phonepe.cricketscoreboard.common.response.CreateGameResponse;
import com.phonepe.cricketscoreboard.common.resquest.CreateGameRequest;

public interface IGameService {
    CreateGameResponse createGame(CreateGameRequest createGameRequest);
}
