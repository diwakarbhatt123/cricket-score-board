package com.phonepe.cricketscoreboard.common.resquest;

import com.phonepe.cricketscoreboard.common.dto.Ball;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AddBallRequest {
    private String gameId;
    private String teamId;
    private int overNumber;
    private Ball ball;
}
