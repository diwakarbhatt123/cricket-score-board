package com.phonepe.cricketscoreboard.service;

import com.phonepe.cricketscoreboard.common.response.GameScoreBoardResponse;
import com.phonepe.cricketscoreboard.common.response.TeamScoreBoardResponse;
import com.phonepe.cricketscoreboard.common.resquest.AddBallRequest;

public interface ITeamService {
    GameScoreBoardResponse addBallScore(AddBallRequest addBallRequest);

    TeamScoreBoardResponse getTeamScoreBoard(String gameId, String teamId);

    GameScoreBoardResponse getGameScoreBoard(String gameId);
}
