package com.phonepe.cricketscoreboard.repository;

import com.phonepe.cricketscoreboard.common.dto.GameScoreBoard;
import com.phonepe.cricketscoreboard.common.resquest.AddBallRequest;

public interface IScoreBoardDao {
    void updateOver(AddBallRequest addBallRequest);

    GameScoreBoard createScoreBoard(String gameId);

    GameScoreBoard gameScoreByGameId(String gameId);
}

