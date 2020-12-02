package com.phonepe.cricketscoreboard.controller;

import com.phonepe.cricketscoreboard.common.constants.RestConstant;
import com.phonepe.cricketscoreboard.common.response.CreateGameResponse;
import com.phonepe.cricketscoreboard.common.response.GameScoreBoardResponse;
import com.phonepe.cricketscoreboard.common.response.TeamScoreBoardResponse;
import com.phonepe.cricketscoreboard.common.resquest.AddBallRequest;
import com.phonepe.cricketscoreboard.common.resquest.CreateGameRequest;
import com.phonepe.cricketscoreboard.service.IGameService;
import com.phonepe.cricketscoreboard.service.ITeamService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RestConstant.BASE_URL_V1)
public class ControllerGameController extends ControllerExceptionHandler {
    private IGameService gameService;
    private ITeamService teamService;

    public ControllerGameController(IGameService gameService, ITeamService teamService) {
        this.gameService = gameService;
        this.teamService = teamService;
    }

    @PostMapping(path = RestConstant.CREATE_GAME, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CreateGameResponse createGame(@RequestBody CreateGameRequest createGameRequest) {
        return gameService.createGame(createGameRequest);
    }

    @PostMapping(path = RestConstant.BALL_UPDATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameScoreBoardResponse addBallScore(@RequestBody AddBallRequest addBallRequest) {
        return teamService.addBallScore(addBallRequest);
    }

    @GetMapping(path = RestConstant.TEAM_SCOREBOARD, produces = MediaType.APPLICATION_JSON_VALUE)
    public TeamScoreBoardResponse getTeamScoreBoard(@PathVariable("gameId") String gameId, @PathVariable("teamId") String teamId) {
        return teamService.getTeamScoreBoard(gameId, teamId);
    }

    @GetMapping(path = RestConstant.GAME_RESULT, produces = MediaType.APPLICATION_JSON_VALUE)
    public GameScoreBoardResponse getGameScoreBoard(@PathVariable("gameId") String gameId) {
        return teamService.getGameScoreBoard(gameId);
    }


}
