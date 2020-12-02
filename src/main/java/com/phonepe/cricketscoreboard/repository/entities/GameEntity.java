package com.phonepe.cricketscoreboard.repository.entities;

import com.phonepe.cricketscoreboard.common.dto.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameEntity {
    private int numberOfTeamPlayers;
    private int overs;
    private List<Team> teamList;
    private boolean playingTeam;
    private String winnerTeam;
}
