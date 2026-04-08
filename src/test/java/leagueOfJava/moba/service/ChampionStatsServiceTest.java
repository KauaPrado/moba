package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.domain.ChampionStats;
import leagueOfJava.moba.dto.ChampionStatsDTO;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.repository.ChampionStatsRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(SpringExtension.class)
class ChampionStatsServiceTest {

    @InjectMocks
    private ChampionStatsService championStatsService;

    @Mock
    private ChampionStatsRepository championStatsRepositoryMock;

    @Mock
    private ChampionRepository championRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    private Champion validChampion;
    private ChampionStats validChampionStats;
    private ChampionStatsDTO validChampionStatsDTO;

    @BeforeEach
    void setUp() {

        validChampion = ChampionCreator.createValidChampion();


        validChampionStats = ChampionStats.builder()
                .id(1L)
                .name("Estatísticas do Malzahar")
                .win_rate("52.5%")
                .pick_rate("4.2%")
                .ban_rate("1.5%")
                .champion(validChampion)
                .build();


        validChampionStatsDTO = new ChampionStatsDTO();
        validChampionStatsDTO.setName("Estatísticas do Malzahar");
        validChampionStatsDTO.setWin_rate("52.5%");
        validChampionStatsDTO.setPick_rate("4.2%");
        validChampionStatsDTO.setBan_rate("1.5%");
        validChampionStatsDTO.setChampionID(1L);


        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(validChampion));

        BDDMockito.when(modelMapperMock.map(any(), ArgumentMatchers.eq(ChampionStatsDTO.class)))
                .thenReturn(validChampionStatsDTO);
    }

    @Test
    @DisplayName("register salva estatísticas quando campeão existe")
    void register_SavesChampionStats_WhenChampionExists() {

        Assertions.assertThatCode(() -> championStatsService.register(validChampionStatsDTO))
                .doesNotThrowAnyException();


        BDDMockito.verify(championStatsRepositoryMock, BDDMockito.times(1)).save(any(ChampionStats.class));
    }

    @Test
    @DisplayName("register lança ResultadoNaoEncontradoException quando campeão não existe")
    void register_ThrowsResultadoNaoEncontradoException_WhenChampionDoesNotExist() {

        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.empty());


        Assertions.assertThatExceptionOfType(ResultadoNaoEncontradoException.class)
                .isThrownBy(() -> championStatsService.register(validChampionStatsDTO))
                .withMessageContaining("Champion não encontrado para o id informado");
    }

    @Test
    @DisplayName("getAll retorna página de ChampionStatsDTO quando bem-sucedido")
    void getAll_ReturnsPageOfChampionStatsDTO_WhenSuccessful() {
        PageImpl<ChampionStats> statsPage = new PageImpl<>(List.of(validChampionStats));

        BDDMockito.when(championStatsRepositoryMock.findAll(any(PageRequest.class)))
                .thenReturn(statsPage);

        Page<ChampionStatsDTO> result = championStatsService.getAll(PageRequest.of(1, 1));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(result.toList().get(0).getName()).isEqualTo(validChampionStatsDTO.getName());
        Assertions.assertThat(result.toList().get(0).getWin_rate()).isEqualTo(validChampionStatsDTO.getWin_rate());
    }
}