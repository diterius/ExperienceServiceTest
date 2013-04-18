package org.prg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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

    public List<GameStatistic> getStatistics(int userId, Date from, Date to) {
        TreeSet<GameStatistic> set = new TreeSet<GameStatistic>(statistics.get(userId));

        GameStatistic gsFrom = new GameStatistic();
        gsFrom.setStartTimestamp(from);
        
        Set<GameStatistic> subset = null; 
        
        if (to!=null) {
            GameStatistic gsTo = new GameStatistic();
            gsTo.setStartTimestamp(to);
            subset = set.subSet(gsFrom, true, gsTo, true);
            subset.remove(gsTo);
        } else {
            subset = set.tailSet(gsFrom);
        }
        subset.remove(gsFrom);
        
        return new ArrayList<GameStatistic>(subset);
    }

}
