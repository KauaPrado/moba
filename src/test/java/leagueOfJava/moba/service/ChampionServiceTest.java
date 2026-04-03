package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.dto.ChampionDTO;
import leagueOfJava.moba.exception.DadosInvalidosException;
import leagueOfJava.moba.exception.NomeJaExistenteException;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.util.ChampionCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(SpringExtension.class)
class ChampionServiceTest {

    @InjectMocks
    private ChampionService championService;

    @Mock
    private ChampionRepository championRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    @BeforeEach
    void setUp() {
        Champion champion = ChampionCreator.createValidChampion();
        ChampionDTO championDTO = new ChampionDTO();

        // Mock do Page
        PageImpl<Champion> championPage = new PageImpl<>(List.of(champion));
        BDDMockito.when(championRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(championPage);

        // Mock do ModelMapper
        BDDMockito.when(modelMapperMock.map(any(), eq(ChampionDTO.class))).thenReturn(championDTO);
        BDDMockito.when(modelMapperMock.map(any(), eq(Champion.class))).thenReturn(champion);
    }

    @Test
    @DisplayName("register lança NomeJaExistenteException quando nome já existe")
    void register_ThrowsNomeJaExistenteException_WhenNameAlreadyExists() {
        ChampionDTO dto = ChampionCreator.createValidChampionDTO();
        BDDMockito.when(championRepositoryMock.findByName(anyString()))
                .thenReturn(Optional.of(ChampionCreator.createValidChampion()));

        Assertions.assertThatExceptionOfType(NomeJaExistenteException.class)
                .isThrownBy(() -> championService.register(dto));
    }

    @Test
    @DisplayName("findById lança ResultadoNaoEncontradoException quando ID não existe")
    void findById_ThrowsResultadoNaoEncontradoException_WhenIdDoesNotExist() {
        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResultadoNaoEncontradoException.class)
                .isThrownBy(() -> championService.findById(1L));
    }

    @Test
    @DisplayName("activate lança DadosInvalidosException quando campeão já está ativo")
    void activate_ThrowsDadosInvalidosException_WhenChampionIsAlreadyActive() {
        Champion activeChampion = ChampionCreator.createValidChampion();
        activeChampion.setActive(true);

        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(activeChampion));

        Assertions.assertThatExceptionOfType(DadosInvalidosException.class)
                .isThrownBy(() -> championService.activate(1L))
                .withMessageContaining("o campeão já se encontra ativo");
    }

    @Test
    @DisplayName("inactivate altera status para false quando bem-sucedido")
    void inactivate_ChangesStatusToFalse_WhenSuccessful() {
        Champion activeChampion = ChampionCreator.createValidChampion();
        activeChampion.setActive(true);

        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(activeChampion));

        championService.inactivate(1L);

        Assertions.assertThat(activeChampion.isActive()).isFalse();
        BDDMockito.verify(championRepositoryMock).save(activeChampion);
    }

    @Test
    @DisplayName("deleteByID chama delete quando ID existe")
    void deleteByID_CallsDelete_WhenIdExists() {

        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(ChampionCreator.createValidChampion()));

        Assertions.assertThatCode(() -> championService.deleteByID(1L))
                .doesNotThrowAnyException();

        BDDMockito.verify(championRepositoryMock).deleteById(anyLong());
    }
}