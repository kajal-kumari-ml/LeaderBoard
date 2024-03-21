package com.gfg.leaderboard.exception;

public class AlreadyExistingEmailId extends RuntimeException{
    public AlreadyExistingEmailId(String message) {
        super(message);
    }
    
}
