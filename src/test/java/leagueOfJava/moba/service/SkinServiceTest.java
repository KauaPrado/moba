package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.domain.Skin;
import leagueOfJava.moba.dto.SkinDTO;
import leagueOfJava.moba.exception.DadosInvalidosException;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.repository.SkinRepository;
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
class SkinServiceTest {

    @InjectMocks
    private SkinService skinService;

    @Mock
    private SkinRepository skinRepositoryMock;

    @Mock
    private ChampionRepository championRepositoryMock;

    @Mock
    private ModelMapper modelMapperMock;

    private Skin validSkin;
    private SkinDTO validSkinDTO;
    private Champion validChampion;

    @BeforeEach
    void setUp() {

        validChampion = ChampionCreator.createValidChampion();

        validSkin = new Skin();
        validSkin.setId(1L);
        validSkin.setName("Malzahar Chefão");
        validSkin.setActive(true);
        validSkin.setChampion(validChampion);

        validSkinDTO = SkinDTO.builder()

                .name("Malzahar Chefão")
                .championID(1L)
                .active(true)
                .build();


        BDDMockito.when(skinRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(validSkin));

        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.of(validChampion));

        BDDMockito.when(modelMapperMock.map(any(), ArgumentMatchers.eq(SkinDTO.class)))
                .thenReturn(validSkinDTO);
    }

    @Test
    @DisplayName("register salva skin quando campeão existe")
    void register_SavesSkin_WhenChampionExists() {
        Assertions.assertThatCode(() -> skinService.register(validSkinDTO))
                .doesNotThrowAnyException();


        BDDMockito.verify(skinRepositoryMock, BDDMockito.times(1)).save(any(Skin.class));
    }

    @Test
    @DisplayName("register lança ResultadoNaoEncontradoException quando campeão não existe")
    void register_ThrowsResultadoNaoEncontradoException_WhenChampionDoesNotExist() {

        BDDMockito.when(championRepositoryMock.findById(anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(ResultadoNaoEncontradoException.class)
                .isThrownBy(() -> skinService.register(validSkinDTO))
                .withMessageContaining("Champion não encontrado");
    }

    @Test
    @DisplayName("getAll retorna página de SkinDTO quando bem-sucedido")
    void getAll_ReturnsPageOfSkinDTO_WhenSuccessful() {
        PageImpl<Skin> skinPage = new PageImpl<>(List.of(validSkin));
        BDDMockito.when(skinRepositoryMock.findAll(any(PageRequest.class)))
                .thenReturn(skinPage);

        Page<SkinDTO> result = skinService.getAll(PageRequest.of(1, 1));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.toList()).isNotEmpty().hasSize(1);
        Assertions.assertThat(result.toList().get(0).getName()).isEqualTo(validSkinDTO.getName());
    }

    @Test
    @DisplayName("inactivate lança DadosInvalidosException quando skin já está inativa")
    void inactivate_ThrowsDadosInvalidosException_WhenSkinIsAlreadyInactive() {
        validSkin.setActive(false);

        Assertions.assertThatExceptionOfType(DadosInvalidosException.class)
                .isThrownBy(() -> skinService.inactivate(1L))
                .withMessageContaining("a skin já se encontra inativa");
    }

    @Test
    @DisplayName("inactivate altera status para false quando bem-sucedido")
    void inactivate_ChangesStatusToFalse_WhenSuccessful() {

        skinService.inactivate(1L);

        Assertions.assertThat(validSkin.isActive()).isFalse();
        BDDMockito.verify(skinRepositoryMock).save(validSkin);
    }

    @Test
    @DisplayName("activate lança DadosInvalidosException quando skin já está ativa")
    void activate_ThrowsDadosInvalidosException_WhenSkinIsAlreadyActive() {

        Assertions.assertThatExceptionOfType(DadosInvalidosException.class)
                .isThrownBy(() -> skinService.activate(1L))
                .withMessageContaining("a skin já se encontra ativa");
    }
}