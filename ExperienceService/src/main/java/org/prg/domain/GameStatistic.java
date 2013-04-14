package org.prg.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "game_statistic")
public class GameStatistic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name="user_id")
    private int userId;
    
    @Column(name="start_time")
    private Date startTimestamp;
    
    @Column(name="finish_time")
    private Date endTimestamp;
    
    @Column(name="game_points")
    private int points;
    
    @Version
    private long version;
    
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
}
