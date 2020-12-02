package com.phonepe.cricketscoreboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {"com.phonepe.*"})
public class CricketScoreBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(CricketScoreBoardApplication.class, args);
    }

}
