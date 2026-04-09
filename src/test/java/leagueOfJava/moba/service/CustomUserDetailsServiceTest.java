package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.User;
import leagueOfJava.moba.enums.RoleEnum;
import leagueOfJava.moba.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para Custom User Details Service")
class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private UserRepository userRepositoryMock;

    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = User.builder()
                .id(1L)
                .login("faker")
                .password("senha_criptografada")
                .role(RoleEnum.USER)
                .build();
    }

    @Test
    @DisplayName("loadUserByUsername retorna UserDetails quando o usuário existe")
    void loadUserByUsername_ReturnsUserDetails_WhenUserExists() {
        BDDMockito.when(userRepositoryMock.findByLogin(ArgumentMatchers.anyString()))
                .thenReturn(validUser);

        UserDetails result = customUserDetailsService.loadUserByUsername("faker");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getUsername()).isEqualTo("faker");
        Assertions.assertThat(result.getPassword()).isEqualTo("senha_criptografada");
    }

    @Test
    @DisplayName("loadUserByUsername lança UsernameNotFoundException quando o usuário não existe")
    void loadUserByUsername_ThrowsUsernameNotFoundException_WhenUserDoesNotExist() {
        BDDMockito.when(userRepositoryMock.findByLogin(ArgumentMatchers.anyString()))
                .thenReturn(null);

        Assertions.assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> customUserDetailsService.loadUserByUsername("usuario_fantasma"))
                .withMessageContaining("Usuário não encontrado");
    }
}