package org.prg.service;

import java.util.List;

import org.prg.domain.GameEvent;
import org.prg.domain.GameStatistic;

public interface ExperienceService {
    
    void receiveEvent(GameEvent event);
    List<GameStatistic> getGameStatistics(int userId);
    boolean hasIncomingEvents();
}
