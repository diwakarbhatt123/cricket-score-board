package com.phonepe.cricketscoreboard.common.resquest;

import com.phonepe.cricketscoreboard.common.dto.Team;
import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateGameRequest extends AbstractRequest {
    List<Team> teams;
    private int noOfPlayersInTeam;
    private int overs;
    private boolean playingTeam;
}
