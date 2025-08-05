package leagueOfJava.moba.repository;

import leagueOfJava.moba.domain.Champion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChampionRepository extends JpaRepository <Champion, Long> {

    @Query("SELECT entity FROM Champion entity WHERE active = true")
    List<Champion> findAllActive();

    @Query("SELECT entity FROM Champion entity WHERE LOWER(name) = :name")
    Optional<Champion> findByName(@Param("name") String name);
}
