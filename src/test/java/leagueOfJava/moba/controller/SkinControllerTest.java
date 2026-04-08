package leagueOfJava.moba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leagueOfJava.moba.dto.SkinDTO;
import leagueOfJava.moba.service.SkinService;
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
@DisplayName("Testes para Skin Controller")
class SkinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SkinService skinServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    private SkinDTO createValidSkinDTO() {
        return SkinDTO.builder()
                .name("Malzahar Chefão")
                .championID(1L)
                .active(true)
                .build();
    }

    @Test
    @WithMockUser
    @DisplayName("getAll retorna página de skins quando bem-sucedido")
    void getAll_ReturnsPageOfSkins_WhenSuccessful() throws Exception {
        PageImpl<SkinDTO> skinPage = new PageImpl<>(List.of(createValidSkinDTO()));

        BDDMockito.when(skinServiceMock.getAll(ArgumentMatchers.any()))
                .thenReturn(skinPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/skins"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("Malzahar Chefão"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].championID").value(1L));
    }

    @Test
    @WithMockUser
    @DisplayName("findById retorna skin quando bem-sucedido")
    void findById_ReturnsSkin_WhenSuccessful() throws Exception {
        SkinDTO skinDTO = createValidSkinDTO();

        BDDMockito.when(skinServiceMock.findByID(ArgumentMatchers.anyLong()))
                .thenReturn(skinDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/skins/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Malzahar Chefão"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.championID").value(1L));
    }

    @Test
    @WithMockUser
    @DisplayName("register retorna 201 quando bem-sucedido")
    void register_Returns201_WhenSuccessful() throws Exception {
        SkinDTO skinDTO = createValidSkinDTO();

        mockMvc.perform(MockMvcRequestBuilders.post("/skins")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(skinDTO)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        BDDMockito.verify(skinServiceMock).register(ArgumentMatchers.any(SkinDTO.class));
    }

    @Test
    @WithMockUser
    @DisplayName("inactivate retorna 200 quando bem-sucedido")
    void inactivate_Returns200_WhenSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/skins/inactivate/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(skinServiceMock).inactivate(1L);
    }

    @Test
    @WithMockUser
    @DisplayName("activate retorna 200 quando bem-sucedido")
    void activate_Returns200_WhenSuccessful() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/skins/activate/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

        BDDMockito.verify(skinServiceMock).activate(1L);
    }
}