package org.prg.test.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.prg.domain.EventType;
import org.prg.domain.GameEvent;

public class TestUtil {

    public static GameEvent createSingleEvent(int userId, EventType type, int points) {
        GameEvent event = new GameEvent();

        event.setUserId(userId);
        event.setTimestamp(new Date());
        event.setType(type);
        event.setPoints(points);
        
        return event;
    }
    
    public static List<GameEvent> generateEventSequence(int userId) {
        List<GameEvent> list = new ArrayList<GameEvent>();
        
        GameEvent startEvent = new GameEvent();
        startEvent.setUserId(userId);
        startEvent.setTimestamp(new Date());
        startEvent.setType(EventType.START);
        
        list.add(startEvent);
        
        Random rnd = new Random();
        int eventCount = rnd.nextInt(10);
        
        for (int i = 0; i < eventCount; i++) {
            int points = rnd.nextInt(50);
            
            GameEvent gameEvent = new GameEvent();
            gameEvent.setUserId(userId);
            gameEvent.setTimestamp(new Date());
            gameEvent.setType(EventType.EXPERIENCE);
            gameEvent.setPoints(points);
            
            list.add(gameEvent);
        }
        
        GameEvent finishEvent = new GameEvent();
        finishEvent.setUserId(userId);
        finishEvent.setTimestamp(new Date());
        finishEvent.setType(EventType.FINISH);
        
        list.add(finishEvent);
        
        return list;
    }
    
}
