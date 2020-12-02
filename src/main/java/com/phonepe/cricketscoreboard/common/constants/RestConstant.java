package com.phonepe.cricketscoreboard.common.constants;

public abstract class RestConstant {
    public static final String BASE_URL = "/cricket/game/v1";
    public final static String BASE_URL_V1 = BASE_URL + "v1";
    public final static String CREATE_GAME = "/create";
    public final static String BALL_UPDATE = "/ball/update";
    public final static String TEAM_SCOREBOARD = "{gameId}/team/{teamId}";
    public final static String GAME_RESULT = "/result/{gameId}";
}
