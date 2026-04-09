package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(SpringExtension.class)
@DisplayName("Testes para Token Service")
class TokenServiceTest {

    @InjectMocks
    private TokenService tokenService;

    private User validUser;

    private final String SECRET_TEST = "meu_segredo_super_seguro_para_testes_do_moba_123";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "secret", SECRET_TEST);

        validUser = User.builder()
                .login("faker")
                .password("senha_criptografada")
                .build();
    }

    @Test
    @DisplayName("generateToken retorna um token JWT válido quando bem-sucedido")
    void generateToken_ReturnsValidJwtToken_WhenSuccessful() {
        String token = tokenService.generateToken(validUser);

        Assertions.assertThat(token).isNotNull();
        Assertions.assertThat(token).isNotEmpty();

        Assertions.assertThat(token.split("\\.")).hasSize(3);
    }

    @Test
    @DisplayName("validateToken retorna o login (subject) quando o token é válido")
    void validateToken_ReturnsSubject_WhenTokenIsValid() {
        String token = tokenService.generateToken(validUser);

        String subject = tokenService.validateToken(token);

        Assertions.assertThat(subject).isNotNull();
        Assertions.assertThat(subject).isEqualTo(validUser.getLogin());
    }

    @Test
    @DisplayName("validateToken retorna null quando o token for inválido, expirado ou forjado")
    void validateToken_ReturnsNull_WhenTokenIsInvalid() {
        String invalidToken = "um.token.totalmente.invalido";

        String subject = tokenService.validateToken(invalidToken);

        Assertions.assertThat(subject).isNull();
    }
}