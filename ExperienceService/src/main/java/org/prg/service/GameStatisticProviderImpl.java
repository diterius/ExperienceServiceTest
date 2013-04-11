package org.prg.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.prg.domain.GameStatistic;
import org.springframework.stereotype.Component;

@Component
public class GameStatisticProviderImpl implements GameStatisticProvider {

    private ConcurrentHashMap<Integer, ArrayList<GameStatistic>> statistics = new ConcurrentHashMap<Integer, ArrayList<GameStatistic>>();
    
    public void store(GameStatistic stat) {
        Integer userId = Integer.valueOf(stat.getUserId());
        if (statistics.containsKey(userId)) {
            ArrayList<GameStatistic> userStat = statistics.get(userId);
            userStat.add(stat);
        } else {
            ArrayList<GameStatistic> userStat = new ArrayList<GameStatistic>();
            userStat.add(stat);
            statistics.put(userId, userStat);
        }
    }

    public List<GameStatistic> getStatistics(int userId) {
        return statistics.get(userId);
    }

}
