package org.prg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.prg.domain.GameStatistic;
import org.prg.repository.GameStatisticRepository;
import org.prg.repository.GameStatisticSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Repository;

@Repository
public class GameStatisticProviderImpl implements GameStatisticProvider<GameStatistic> {

    private ConcurrentHashMap<Integer, ArrayList<GameStatistic>> statistics = new ConcurrentHashMap<Integer, ArrayList<GameStatistic>>();
    
    @Resource
    private GameStatisticRepository repo;
    
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
        
        repo.save(stat);
    }

    public List<GameStatistic> getStatistics(int userId, Date from, Date to) {
//        return statistics.get(userId);
        Specification<GameStatistic> spec = Specifications
                .where(GameStatisticSpecification.fromDate(from))
                    .and(GameStatisticSpecification.isUser(userId))
                    ;
        if (to!=null) spec = Specifications.where(spec).and(GameStatisticSpecification.toDate(to));
        return repo.findAll(spec);
    }

}
