package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.User;
import leagueOfJava.moba.dto.AuthenticationDto;
import leagueOfJava.moba.dto.RegisterDTO;
import leagueOfJava.moba.enums.RoleEnum;
import leagueOfJava.moba.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para Authentication Service")
class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private AuthenticationManager authenticationManagerMock;

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    private RegisterDTO validRegisterDTO;
    private AuthenticationDto validAuthDTO;

    @BeforeEach
    void setUp() {
        validRegisterDTO = new RegisterDTO("faker", "senha_plana123", RoleEnum.USER);
        validAuthDTO = new AuthenticationDto("faker", "senha_plana123");

        BDDMockito.when(passwordEncoderMock.encode(ArgumentMatchers.anyString()))
                .thenReturn("$2a$10$FakeHashCriptografadoGeradoPeloBcrypt123");
    }

    @Test
    @DisplayName("register salva um novo usuário com senha criptografada quando bem-sucedido")
    void register_SavesUserWithEncodedPassword_WhenSuccessful() {
        Assertions.assertThatCode(() -> authenticationService.register(validRegisterDTO))
                .doesNotThrowAnyException();

        BDDMockito.verify(userRepositoryMock, BDDMockito.times(1)).save(ArgumentMatchers.any(User.class));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        BDDMockito.verify(userRepositoryMock).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        Assertions.assertThat(savedUser.getLogin()).isEqualTo(validRegisterDTO.getLogin());
        Assertions.assertThat(savedUser.getRole()).isEqualTo(validRegisterDTO.getRole());
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("$2a$10$FakeHashCriptografadoGeradoPeloBcrypt123");
        Assertions.assertThat(savedUser.getPassword()).isNotEqualTo(validRegisterDTO.getPassword());
    }

    @Test
    @DisplayName("login autentica com sucesso quando credenciais são válidas")
    void login_Authenticates_WhenCredentialsAreValid() {
        Assertions.assertThatCode(() -> authenticationService.login(validAuthDTO))
                .doesNotThrowAnyException();

        BDDMockito.verify(authenticationManagerMock, BDDMockito.times(1))
                .authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    @DisplayName("login lança exceção quando credenciais são inválidas")
    void login_ThrowsException_WhenCredentialsAreInvalid() {
        BDDMockito.doThrow(new BadCredentialsException("Bad credentials"))
                .when(authenticationManagerMock)
                .authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class));

        Assertions.assertThatExceptionOfType(BadCredentialsException.class)
                .isThrownBy(() -> authenticationService.login(validAuthDTO));
    }
}