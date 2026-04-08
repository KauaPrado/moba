package leagueOfJava.moba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leagueOfJava.moba.dto.ChampionStatsDTO;
import leagueOfJava.moba.service.ChampionStatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para Champion Stats Controller")
class ChampionStatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChampionStatsService championStatsServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private ChampionStatsDTO createValidChampionStatsDTO() {
        ChampionStatsDTO dto = new ChampionStatsDTO();
        dto.setName("Estatísticas do Malzahar");
        dto.setWin_rate("52.5%");
        dto.setPick_rate("4.2%");
        dto.setBan_rate("1.5%");
        dto.setChampionID(1L);
        return dto;
    }

    @Test
    @WithMockUser
    @DisplayName("getAll retorna página de estatísticas quando bem-sucedido")
    void getAll_ReturnsPageOfStats_WhenSuccessful() throws Exception {
        PageImpl<ChampionStatsDTO> statsPage = new PageImpl<>(List.of(createValidChampionStatsDTO()));

        BDDMockito.when(championStatsServiceMock.getAll(ArgumentMatchers.any()))
                .thenReturn(statsPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/championStats"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Estatísticas do Malzahar"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].win_rate").value("52.5%"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].championID").value(1L));
    }

    @Test
    @WithMockUser
    @DisplayName("register retorna 201 quando bem-sucedido")
    void register_Returns201_WhenSuccessful() throws Exception {
        ChampionStatsDTO dto = createValidChampionStatsDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/championStats")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        BDDMockito.verify(championStatsServiceMock).register(ArgumentMatchers.any(ChampionStatsDTO.class));
    }
}