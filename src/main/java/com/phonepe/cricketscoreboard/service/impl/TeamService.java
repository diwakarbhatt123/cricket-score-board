package com.phonepe.cricketscoreboard.service.impl;

import com.phonepe.cricketscoreboard.common.constants.CricketConstant;
import com.phonepe.cricketscoreboard.common.dto.Ball;
import com.phonepe.cricketscoreboard.common.dto.GameScoreBoard;
import com.phonepe.cricketscoreboard.common.dto.PlayerScore;
import com.phonepe.cricketscoreboard.common.dto.TeamScoreBoard;
import com.phonepe.cricketscoreboard.common.enums.ResultType;
import com.phonepe.cricketscoreboard.common.enums.WicketType;
import com.phonepe.cricketscoreboard.common.exception.CricketGameException;
import com.phonepe.cricketscoreboard.common.exception.ExceptionMessage;
import com.phonepe.cricketscoreboard.common.response.GameScoreBoardResponse;
import com.phonepe.cricketscoreboard.common.response.TeamScoreBoardResponse;
import com.phonepe.cricketscoreboard.common.resquest.AddBallRequest;
import com.phonepe.cricketscoreboard.repository.IGameDao;
import com.phonepe.cricketscoreboard.repository.IScoreBoardDao;
import com.phonepe.cricketscoreboard.repository.entities.GameEntity;
import com.phonepe.cricketscoreboard.service.ITeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class TeamService implements ITeamService {

    private IGameDao gameDao;
    private IScoreBoardDao scoreBoardDao;

    //Can use bean for initialization
    public TeamService(IGameDao gameDao, IScoreBoardDao scoreBoardDao) {
        this.gameDao = gameDao;
        this.scoreBoardDao = scoreBoardDao;
    }

    @Override
    public GameScoreBoardResponse addBallScore(AddBallRequest addBallRequest) {
        GameEntity gameEntity = gameDao.getGameById(addBallRequest.getGameId());

        if (gameEntity.getOvers() < addBallRequest.getOverNumber()) {
            log.info("Over count {} is more than fixed over count {} ", addBallRequest.getOverNumber(), gameEntity.getOvers());
            throw new CricketGameException(ExceptionMessage.INTERNAL_SERVER_ERROR);
        }

        GameScoreBoard gameScoreBoard = scoreBoardDao.gameScoreByGameId(addBallRequest.getGameId());

        if (gameScoreBoard.getResultType() != ResultType.LIVE || gameScoreBoard.getPlayingTeamCount() == CricketConstant.GAME_TEAM_SIZE) {
            log.info("Game is already over. Can not add ball action for this game");
            throw new CricketGameException(ExceptionMessage.GAME_OVER_ERROR);
        }

        TeamScoreBoard teamScoreBoard = gameScoreBoard.getTeamScoreBoards().get(gameScoreBoard.getPlayingTeamCount());


        if (teamScoreBoard.getTeamId().equals(addBallRequest.getTeamId())) {
            log.info("Team id does not match with batting team");
            throw new CricketGameException(ExceptionMessage.INTERNAL_SERVER_ERROR);
        }

        if (teamScoreBoard.getOverCount() == gameEntity.getOvers()) {
            log.info("Team is already played");
            throw new CricketGameException(ExceptionMessage.INTERNAL_SERVER_ERROR);
        }

        //add ball score
        recordBallAction(gameScoreBoard, teamScoreBoard, addBallRequest.getBall());


        if (teamScoreBoard.getCurrentOverBalls() == CricketConstant.BALLS_IN_OVER) {
            teamScoreBoard.setOverCount(teamScoreBoard.getOverCount() + 1);
            teamScoreBoard.setCurrentOverBalls(0);
            if (teamScoreBoard.getOverCount() == gameEntity.getOvers()) {
                log.info("Team has played all overs");
                gameScoreBoard.setPlayingTeamCount(gameScoreBoard.getPlayingTeamCount() + 1);
            }
        }

        // Team 2 won match
        if (gameScoreBoard.getPlayingTeamCount() == 1 &&
                gameScoreBoard.getTeamScoreBoards().get(0).getTotalRun() < gameScoreBoard.getTeamScoreBoards().get(1).getTotalRun()) {
            updateGameResult(gameScoreBoard);
        }

        //Both team played all over
        if (gameScoreBoard.getPlayingTeamCount() == CricketConstant.GAME_TEAM_SIZE) {
            updateGameResult(gameScoreBoard);
        }

        return GameScoreBoardResponse.builder().gameScoreBoard(gameScoreBoard).build();
    }

    @Override
    public TeamScoreBoardResponse getTeamScoreBoard(String gameId, String teamId) {
        GameScoreBoard gameScoreBoard = scoreBoardDao.gameScoreByGameId(gameId);
//        if(gameScoreBoard.getResultType()!=ResultType.LIVE || gameScoreBoard.getPlayingTeamCount()==CricketConstant.GAME_TEAM_SIZE){
//            log.info("Game is already over. Can not add ball action for this game");
//            throw new CricketGameException(ExceptionMessage.GAME_OVER_ERROR);
//        }
        TeamScoreBoard teamScoreBoard = gameScoreBoard.getTeamScoreBoards().stream().filter(scoreBoard -> scoreBoard.getTeamId().equals(teamId)).findFirst().get();
        if (Objects.isNull(teamScoreBoard)) {
            log.info("Team id does not match with batting team");
            throw new CricketGameException(ExceptionMessage.INTERNAL_SERVER_ERROR);
        }
        return TeamScoreBoardResponse.builder().teamScoreBoard(teamScoreBoard).build();
    }

    @Override
    public GameScoreBoardResponse getGameScoreBoard(String gameId) {
        GameScoreBoard gameScoreBoard = scoreBoardDao.gameScoreByGameId(gameId);
        return GameScoreBoardResponse.builder()
                .gameScoreBoard(gameScoreBoard)
                .build();
    }


    //Need to create ballActionService
    private void recordBallAction(GameScoreBoard gameScoreBoard, TeamScoreBoard teamScoreBoard, Ball ball) {
        switch (ball.getBallStatus()) {
            case WICKET:
                updateWicket(gameScoreBoard, teamScoreBoard);
                break;
            case NORMAL:
                updateRun(teamScoreBoard, ball);
                break;
            case WIDE:
                updateWideRun(teamScoreBoard, ball);
                break;
            default:
                log.info("No other ball status is supported");
        }
    }

    private void updateWicket(GameScoreBoard gameScoreBoard, TeamScoreBoard teamScoreBoard) {
        int players = teamScoreBoard.getPlayerScoreList().size();
        int nextStrikePlayer = 0;
        int nextNonStrikePlayer = 0;

        for (int i = 0; i < players; i++) {
            if (teamScoreBoard.getPlayerScoreList().get(i).equals(teamScoreBoard.getOnStrikePlayer())) {
                nextStrikePlayer = i + 1;
                teamScoreBoard.getPlayerScoreList().get(i).setWicketType(WicketType.HIT_WICKET);
            }
            if (teamScoreBoard.getPlayerScoreList().get(i).equals(teamScoreBoard.getNonStrikePlayer())) {
                nextNonStrikePlayer = i + 1;
                teamScoreBoard.getPlayerScoreList().get(i).setWicketType(WicketType.HIT_WICKET);
            }
        }
        if (nextStrikePlayer < nextNonStrikePlayer) {
            nextStrikePlayer = nextNonStrikePlayer;
        }

        if (players == nextStrikePlayer) {
            log.info("All player of team is out");
            gameScoreBoard.setPlayingTeamCount(gameScoreBoard.getPlayingTeamCount() + 1);
            return;
        }

        teamScoreBoard.setOnStrikePlayer(teamScoreBoard.getPlayerScoreList().get(nextStrikePlayer).getPlayerId());
        teamScoreBoard.setCurrentOverBalls(teamScoreBoard.getCurrentOverBalls() + 1);
        teamScoreBoard.setTotalWicket(teamScoreBoard.getTotalWicket() + 1);
    }

    private void updateRun(TeamScoreBoard teamScoreBoard, Ball ball) {
        switch (ball.getRunType()) {
            case NORMAL:
                for (PlayerScore playerScore : teamScoreBoard.getPlayerScoreList()) {
                    if (playerScore.getPlayerId().equals(teamScoreBoard.getOnStrikePlayer())) {
                        playerScore.setScore(playerScore.getScore() + ball.getRun());
                        break;
                    }
                }

                break;
            case SIX:
                for (PlayerScore playerScore : teamScoreBoard.getPlayerScoreList()) {
                    if (playerScore.getPlayerId().equals(teamScoreBoard.getOnStrikePlayer())) {
                        playerScore.setScore(playerScore.getScore() + ball.getRun());
                        playerScore.setSixesCount(playerScore.getSixesCount() + 1);
                        break;
                    }
                }

                break;
            case FOUR:
                for (PlayerScore playerScore : teamScoreBoard.getPlayerScoreList()) {
                    if (playerScore.getPlayerId().equals(teamScoreBoard.getOnStrikePlayer())) {
                        playerScore.setScore(playerScore.getScore() + ball.getRun());
                        playerScore.setFoursCount(playerScore.getFoursCount() + 1);
                        break;
                    }

                }
                break;
            default:
                log.info("No other runtype is supported");
        }
        teamScoreBoard.setTotalRun(teamScoreBoard.getTotalRun() + ball.getRun());
        teamScoreBoard.setCurrentOverBalls(teamScoreBoard.getCurrentOverBalls() + 1);

        if ((ball.getBallNumber() != 6 && ball.getRun() % 2 == 1) || (ball.getBallNumber() == 6 && ball.getRun() % 2 == 0)) {
            switchStrike(teamScoreBoard);
        }

    }

    private void switchStrike(TeamScoreBoard teamScoreBoard) {
        String tplayerId = teamScoreBoard.getOnStrikePlayer();
        log.info("Team onsttrike: {} and nonStrike:{}", teamScoreBoard.getOnStrikePlayer(), teamScoreBoard.getNonStrikePlayer());
        teamScoreBoard.setOnStrikePlayer(teamScoreBoard.getNonStrikePlayer());
        teamScoreBoard.setNonStrikePlayer(tplayerId);
        log.info("Team onsttrike: {} and nonStrike:{}", teamScoreBoard.getOnStrikePlayer(), teamScoreBoard.getNonStrikePlayer());
    }

    private void updateWideRun(TeamScoreBoard teamScoreBoard, Ball ball) {
        teamScoreBoard.setTotalRun(teamScoreBoard.getTotalRun() + ball.getRun());
    }

    private void updateGameResult(GameScoreBoard gameScoreBoard) {
        TeamScoreBoard firstTeamScoreBoard = gameScoreBoard.getTeamScoreBoards().get(0);
        TeamScoreBoard secondTeamScoreBoard = gameScoreBoard.getTeamScoreBoards().get(1);
        if (firstTeamScoreBoard.getTotalRun() == secondTeamScoreBoard.getTotalRun()) {
            gameScoreBoard.setResultType(ResultType.DRAW);
            gameScoreBoard.setResultMessage("Match draw");
        } else if (firstTeamScoreBoard.getTotalRun() > secondTeamScoreBoard.getTotalRun()) {
            gameScoreBoard.setResultType(ResultType.WINNER);
            int winningRun = firstTeamScoreBoard.getTotalRun() - secondTeamScoreBoard.getTotalRun();
            gameScoreBoard.setResultMessage("Team 1 won match by " + winningRun + " runs");
        } else {
            gameScoreBoard.setResultType(ResultType.WINNER);
            int winningWicket = secondTeamScoreBoard.getPlayerScoreList().size() - secondTeamScoreBoard.getTotalWicket();
            gameScoreBoard.setResultMessage("Team 2 won match by " + winningWicket + " wickets");
        }
    }
}
