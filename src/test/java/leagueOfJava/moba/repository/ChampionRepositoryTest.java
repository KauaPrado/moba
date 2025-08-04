package leagueOfJava.moba.repository;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.util.ChampionCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("testes para o Champion Repository")
class ChampionRepositoryTest {

    @Autowired
    private ChampionRepository championRepository;

    @Test
    @DisplayName("save criará novo Champion caso haja exito")
    public void save_PersistChampion_WhenSuccessful(){
        Champion championToBeSaved = ChampionCreator.createChampionToBeSaved();
        Champion championSaved = this.championRepository.save(championToBeSaved);
        Assertions.assertThat(championSaved).isNotNull();
        Assertions.assertThat(championSaved.getId()).isNotNull();
        Assertions.assertThat(championSaved.getName()).isEqualTo(championToBeSaved.getName());
        Assertions.assertThat(championSaved.getAbility()).isEqualTo(championToBeSaved.getAbility());
        Assertions.assertThat(championSaved.getChampionType()).isEqualTo(championToBeSaved.getChampionType());
        Assertions.assertThat(championSaved.isHuman()).isEqualTo(championToBeSaved.isHuman());
        Assertions.assertThat(championSaved.isActive()).isEqualTo(championToBeSaved.isActive());
    }

    @Test
    @DisplayName("findById encontrará champion com base no id caso haja exito")
    public void encontraChampionPeloID_CasoBemSuscedido(){
        Champion championToBeSaved = ChampionCreator.createChampionToBeSaved();
        Champion championSaved = this.championRepository.save(championToBeSaved);
        Optional<Champion> championfound = this.championRepository.findById(1L);
        Assertions.assertThat(championfound).isPresent();
        Champion championFinal = championfound.get();
        Assertions.assertThat(championFinal.getId()).isNotNull();
        Assertions.assertThat(championFinal)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(championToBeSaved);
    }




    @Test
    @DisplayName("findById não encontrará champion com base no id inexistente caso haja exito")
    public void naoEncontraCampeaoPeloIdInexistente_CasoBemSuscedido(){
        Champion championToBeSaved = ChampionCreator.createChampionToBeSaved();
        Champion championSaved = this.championRepository.save(championToBeSaved);
        Optional<Champion> championfound = this.championRepository.findById(-10L);
        Assertions.assertThat(championfound).isEmpty();
    }
    @Test
    @DisplayName("findByName encontrará champion com base no id caso haja exito")
    public void encontraChampionPeloName_CasoBemSuscedido(){
        Champion championToBeSaved = ChampionCreator.createChampionToBeSaved();
        Champion championSaved = this.championRepository.save(championToBeSaved);
        Optional<Champion> championfound = this.championRepository.findByName("malzahar");
        Assertions.assertThat(championfound).isPresent();
        Champion championFinal = championfound.get();
        Assertions.assertThat(championFinal)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(championToBeSaved);
    }

    @Test
    @DisplayName("findByName não encontrará champion com base no id inexistente caso haja exito")
    public void naoEncontraCampeaoPeloNameInexistente_CasoBemSuscedido(){
        Champion championToBeSaved = ChampionCreator.createChampionToBeSaved();
        Champion championSaved = this.championRepository.save(championToBeSaved);
        Optional<Champion> championfound = this.championRepository.findByName("nome que nao exista");
        Assertions.assertThat(championfound).isEmpty();
    }

    @Test
    @DisplayName("Remove um campeao caso tenha exito")
    void deletaCampeaoComBaseNoId_CasoBemSuscedido(){
        Champion championToBeSaved = ChampionCreator.createChampionToBeSaved();
        Champion championSaved = this.championRepository.save(championToBeSaved);
        this.championRepository.deleteById(1L);
        Optional<Champion> championOptional = this.championRepository.findById(1L);
        Assertions.assertThat(championOptional).isEmpty();

    }



}