package leagueOfJava.moba.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leagueOfJava.moba.domain.User;
import leagueOfJava.moba.dto.AuthenticationDto;
import leagueOfJava.moba.dto.RegisterDTO;
import leagueOfJava.moba.enums.RoleEnum;
import leagueOfJava.moba.repository.UserRepository;
import leagueOfJava.moba.service.AuthenticationService;
import leagueOfJava.moba.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes para Authenticator Controller")
class AuthenticatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationService authenticationServiceMock;

    @MockitoBean
    private AuthenticationManager authenticationManagerMock;

    @MockitoBean
    private UserRepository userRepositoryMock;

    @MockitoBean
    private TokenService tokenServiceMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("login retorna 200 e o token JWT quando credenciais são válidas")
    void login_Returns200AndToken_WhenCredentialsAreValid() throws Exception {
        AuthenticationDto authDto = new AuthenticationDto("faker", "senha123");
        User fakeUser = User.builder().login("faker").password("senha123").role(RoleEnum.USER).build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(fakeUser, null);

        BDDMockito.when(authenticationManagerMock.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);

        BDDMockito.when(tokenServiceMock.generateToken(ArgumentMatchers.any(User.class)))
                .thenReturn("meu.token.jwt.falso");

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("meu.token.jwt.falso"));
    }

    @Test
    @DisplayName("register retorna 200 quando o usuário é registrado com sucesso")
    void register_Returns200_WhenSuccessful() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("novo_invocador", "senha123", RoleEnum.USER);

        BDDMockito.when(userRepositoryMock.findByLogin(ArgumentMatchers.anyString()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        BDDMockito.verify(authenticationServiceMock).register(ArgumentMatchers.any(RegisterDTO.class));
    }

    @Test
    @DisplayName("register retorna 400 Bad Request quando o login já existe")
    void register_Returns400_WhenLoginAlreadyExists() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("faker", "senha123", RoleEnum.USER);
        User existingUser = User.builder().login("faker").build();

        BDDMockito.when(userRepositoryMock.findByLogin("faker"))
                .thenReturn(existingUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        BDDMockito.verify(authenticationServiceMock, BDDMockito.never()).register(ArgumentMatchers.any(RegisterDTO.class));
    }
}