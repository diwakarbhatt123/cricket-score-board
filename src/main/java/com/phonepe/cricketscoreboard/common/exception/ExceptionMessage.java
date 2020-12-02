package com.phonepe.cricketscoreboard.common.exception;

public abstract class ExceptionMessage {
    public final static String INTERNAL_SERVER_ERROR = "Internal server error occured";
    public static String INVALID_GAME_ID = "Game id does not exist";
    public static String GAME_OVER_ERROR = "Game is already over. Can not add ball action for this game";
}
