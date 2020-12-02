package com.phonepe.cricketscoreboard.common.dto;

import com.phonepe.cricketscoreboard.common.enums.ResultType;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GameScoreBoard {
    private List<TeamScoreBoard> teamScoreBoards;
    private int playingTeamCount;
    private ResultType resultType;
    private String resultMessage;
}
