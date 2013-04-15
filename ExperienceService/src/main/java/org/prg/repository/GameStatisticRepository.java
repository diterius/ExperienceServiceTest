package org.prg.repository;

import org.prg.domain.GameStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GameStatisticRepository extends JpaRepository<GameStatistic, Long>, JpaSpecificationExecutor<GameStatistic>  {

}
