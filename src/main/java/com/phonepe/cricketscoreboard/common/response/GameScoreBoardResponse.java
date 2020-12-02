package com.phonepe.cricketscoreboard.common.response;


import com.phonepe.cricketscoreboard.common.dto.GameScoreBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GameScoreBoardResponse extends AbstractResponse {
    private GameScoreBoard gameScoreBoard;
}
