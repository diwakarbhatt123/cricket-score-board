package com.phonepe.cricketscoreboard.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamScoreBoard {
    private String teamId;
    private List<PlayerScore> playerScoreList;
    private String onStrikePlayer;
    private String nonStrikePlayer;
    private int overCount;
    private int totalWicket;
    private int totalRun;
    private int currentOverBalls;

}
