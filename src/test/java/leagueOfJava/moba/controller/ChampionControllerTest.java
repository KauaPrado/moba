package leagueOfJava.moba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leagueOfJava.moba.dto.ChampionDTO;
import leagueOfJava.moba.service.ChampionService;
import leagueOfJava.moba.util.ChampionCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para Champion Controller")
class ChampionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ChampionService championServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    @DisplayName("getAll retorna página de campeões quando bem-sucedido")
    void getAll_ReturnsPageOfChampions_WhenSuccessful() throws Exception {
        PageImpl<ChampionDTO> championPage = new PageImpl<>(List.of(ChampionCreator.createValidChampionDTO()));

        BDDMockito.when(championServiceMock.getAll(ArgumentMatchers.any()))
                .thenReturn(championPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/champions"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Malzahar"));
    }

    @Test
    @WithMockUser
    @DisplayName("findById retorna campeão quando bem-sucedido")
    void findById_ReturnsChampion_WhenSuccessful() throws Exception {
        ChampionDTO championDTO = ChampionCreator.createValidChampionDTO();

        BDDMockito.when(championServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(championDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/champions/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Malzahar"));
    }

    @Test
    @WithMockUser
    @DisplayName("register retorna 201 quando bem-sucedido")
    void register_Returns201_WhenSuccessful() throws Exception {
        ChampionDTO championDTO = ChampionCreator.createChampionDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/champions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(championDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        BDDMockito.verify(championServiceMock).register(ArgumentMatchers.any(ChampionDTO.class));
    }

    @Test
    @WithMockUser
    @DisplayName("inactivate retorna 200 quando bem-sucedido")
    void inactivate_Returns200_WhenSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/champions/inactivate/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(championServiceMock).inactivate(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("deleteById retorna 200 quando bem-sucedido")
    void deleteById_Returns200_WhenSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/champions/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(championServiceMock).deleteByID(1L);
    }
}