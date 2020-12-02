package com.phonepe.cricketscoreboard.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {
    private String id;
    private String teamName;
    private List<String> playersOrder;
//    private String strikePlayer;
//    private String nonStrikePlayer;
}
