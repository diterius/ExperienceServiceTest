package org.prg.domain;

import java.util.Date;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(GameStatistic.class)
public class GameStatistic_ {
    public static volatile SingularAttribute<GameStatistic, Integer> userId;
    public static volatile SingularAttribute<GameStatistic, Date> startTimestamp;
    public static volatile SingularAttribute<GameStatistic, Date> endTimestamp;
    
}
