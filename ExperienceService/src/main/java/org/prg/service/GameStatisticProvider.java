package org.prg.service;

import java.util.List;

import org.prg.domain.GameStatistic;

public interface GameStatisticProvider {

    public abstract void store(GameStatistic stat);

    public abstract List<GameStatistic> getStatistics(int userId);

}