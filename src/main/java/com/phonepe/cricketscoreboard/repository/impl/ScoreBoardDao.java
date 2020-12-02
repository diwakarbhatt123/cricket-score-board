package com.phonepe.cricketscoreboard.repository.impl;

import com.phonepe.cricketscoreboard.common.dto.GameScoreBoard;
import com.phonepe.cricketscoreboard.common.dto.PlayerScore;
import com.phonepe.cricketscoreboard.common.dto.TeamScoreBoard;
import com.phonepe.cricketscoreboard.common.enums.ResultType;
import com.phonepe.cricketscoreboard.common.exception.CricketGameException;
import com.phonepe.cricketscoreboard.common.exception.ExceptionMessage;
import com.phonepe.cricketscoreboard.common.resquest.AddBallRequest;
import com.phonepe.cricketscoreboard.repository.IGameDao;
import com.phonepe.cricketscoreboard.repository.IScoreBoardDao;
import com.phonepe.cricketscoreboard.repository.entities.GameEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ScoreBoardDao implements IScoreBoardDao {

    private IGameDao gameDao;

    private Map<String, GameScoreBoard> gameScoreBoardMap;

    public ScoreBoardDao(IGameDao gameDao) {
        this.gameDao = gameDao;
        gameScoreBoardMap = new HashMap<>();
    }

    @Override
    public void updateOver(AddBallRequest addBallRequest) {

    }

    @Override
    public GameScoreBoard createScoreBoard(String gameId) {
        GameEntity gameEntity = gameDao.getGameById(gameId);
        List<TeamScoreBoard> teamScoreBoards = gameEntity.getTeamList().stream().map(team -> TeamScoreBoard.builder()
                .playerScoreList(initPlayersScore(team.getPlayersOrder()))
                .onStrikePlayer(team.getPlayersOrder().get(0))
                .nonStrikePlayer(team.getPlayersOrder().get(1))
                .teamId(team.getId())
                .currentOverBalls(0)
                .totalWicket(0)
                .build()).collect(Collectors.toList());
        GameScoreBoard gameScoreBoard = GameScoreBoard.builder()
                .teamScoreBoards(teamScoreBoards)
                .resultType(ResultType.LIVE)
                .resultMessage("Ongoing")
                .build();
        gameScoreBoardMap.put(gameId, gameScoreBoard);
        return gameScoreBoard;
    }

    @Override
    public GameScoreBoard gameScoreByGameId(String gameId) {
        validateGameId(gameId);
        return gameScoreBoardMap.get(gameId);
    }

    private List<PlayerScore> initPlayersScore(List<String> players) {
        List<PlayerScore> playerScores = players.stream().map(player -> PlayerScore.builder()
                .foursCount(0)
                .playedBalls(0)
                .playerId(player)
                .score(0)
                .sixesCount(0)
                .wicketType(null)
                .build()).collect(Collectors.toList());
        return playerScores;
    }

    private void validateGameId(String gameId) {
        if (!gameScoreBoardMap.containsKey(gameId)) {
            log.info("Game id does not exist");
            throw new CricketGameException(ExceptionMessage.INVALID_GAME_ID);
        }
    }
}
