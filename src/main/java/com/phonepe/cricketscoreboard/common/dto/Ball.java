package com.phonepe.cricketscoreboard.common.dto;

import com.phonepe.cricketscoreboard.common.enums.BallStatus;
import com.phonepe.cricketscoreboard.common.enums.RunType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ball {
    private int run;
    private RunType runType;
    private int ballNumber;
    private BallStatus ballStatus;
}
