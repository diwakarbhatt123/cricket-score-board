package com.phonepe.cricketscoreboard.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGameResponse extends AbstractResponse {
    private String gameId;
}
