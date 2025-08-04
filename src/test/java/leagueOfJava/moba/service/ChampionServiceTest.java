package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.util.ChampionCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
class ChampionServiceTest {
    @InjectMocks
    private ChampionService championService;

    @Mock
    private ChampionRepository championRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<Champion> championPage = new PageImpl<>(List.of(ChampionCreator.createValidChampion()));
    }
}
