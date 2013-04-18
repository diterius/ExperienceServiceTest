package org.prg.domain;

import java.util.Date;

public class GameStatistic implements Comparable<GameStatistic>{
    
    private int userId;
    private Date startTimestamp;
    private Date endTimestamp;
    private int points;
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public Date getStartTimestamp() {
        return startTimestamp;
    }
    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
    public Date getEndTimestamp() {
        return endTimestamp;
    }
    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    
    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public String toString() {
        return "User Id: " + userId + " timestamp: " + startTimestamp + " with points: " + points;
    }
    
    @Override
    public int compareTo(GameStatistic o) {
        if (startTimestamp.before(o.startTimestamp)) {  
            return -1;  
        }  
        return 1;  
    }
}
