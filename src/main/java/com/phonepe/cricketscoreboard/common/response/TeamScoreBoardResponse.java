package com.phonepe.cricketscoreboard.common.response;

import com.phonepe.cricketscoreboard.common.dto.TeamScoreBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeamScoreBoardResponse extends AbstractResponse {
    private TeamScoreBoard teamScoreBoard;
}
