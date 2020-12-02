package com.phonepe.cricketscoreboard.repository.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerEntity {
    private String playerId;
    private String playerName;

    //add more info if required
}
