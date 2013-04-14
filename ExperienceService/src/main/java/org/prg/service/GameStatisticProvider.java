package org.prg.service;

import java.util.Date;
import java.util.List;

import org.prg.domain.GameStatistic;

public interface GameStatisticProvider<T extends GameStatistic> {

    public abstract void store(T stat);

    public abstract List<T> getStatistics(int userId, Date from, Date to);

}