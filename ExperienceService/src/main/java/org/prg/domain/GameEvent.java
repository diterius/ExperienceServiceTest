package org.prg.domain;

import java.util.Date;

public class GameEvent {

    private int userId;
    private Date timestamp;
    private int points;
    private EventType type;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
    
    @Override
    public String toString(){
        return "Game Event: "+"user Id: "+userId+", event type: " + type.toString() 
                + (type.equals(EventType.EXPERIENCE) ? ", points="+points : "");
    }
}
