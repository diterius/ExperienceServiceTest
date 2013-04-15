package org.prg.repository;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.prg.domain.GameStatistic;
import org.prg.domain.GameStatistic_;
import org.springframework.data.jpa.domain.Specification;

public class GameStatisticSpecification {

    public static Specification<GameStatistic> fromDate(final Date from) {
        return new Specification<GameStatistic>() {
            @Override
            public Predicate toPredicate(Root<GameStatistic> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.<Date>get(GameStatistic_.startTimestamp) ,from);
            }
        };
    }

    public static Specification<GameStatistic> isUser(final int userId) {
        return new Specification<GameStatistic>() {
            @Override
            public Predicate toPredicate(Root<GameStatistic> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.<Integer>get(GameStatistic_.userId) ,userId);
            }
        };
    }

    public static Specification<GameStatistic> toDate(final Date to) {
        return new Specification<GameStatistic>() {
            @Override
            public Predicate toPredicate(Root<GameStatistic> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.<Date>get(GameStatistic_.endTimestamp) ,to);
            }
        };
    }
    
}

