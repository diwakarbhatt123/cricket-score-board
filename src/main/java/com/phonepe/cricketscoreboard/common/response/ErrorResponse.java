package com.phonepe.cricketscoreboard.common.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse extends AbstractResponse {
    private String message;
    private String errorCode;
}
