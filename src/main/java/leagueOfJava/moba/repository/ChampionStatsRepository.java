package leagueOfJava.moba.repository;

import leagueOfJava.moba.domain.ChampionStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionStatsRepository extends JpaRepository <ChampionStats, Long> {
}
