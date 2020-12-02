package com.phonepe.cricketscoreboard.common.response;

import lombok.Data;

@Data
public abstract class AbstractResponse {
    private String status = "SUCCESS";
}
