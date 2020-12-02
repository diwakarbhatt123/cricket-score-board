package com.phonepe.cricketscoreboard.common.dto;

import com.phonepe.cricketscoreboard.common.enums.WicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlayerScore {
    private String playerId;
    private int score;
    private WicketType wicketType;
    private int foursCount;
    private int sixesCount;
    private int playedBalls;
}
