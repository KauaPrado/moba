package leagueOfJava.moba.repository;

import leagueOfJava.moba.domain.User;
import leagueOfJava.moba.enums.RoleEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Testes para User Repository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("findByLogin retorna UserDetails quando o login existe")
    void findByLogin_ReturnsUserDetails_WhenSuccessful() {
        User userToBeSaved = User.builder()
                .login("kaua_prado")
                .password("senha_criptografada")
                .role(RoleEnum.ADMIN)
                .build();

        userRepository.save(userToBeSaved);


        UserDetails userDetailsFound = userRepository.findByLogin("kaua_prado");


        Assertions.assertThat(userDetailsFound).isNotNull();
        Assertions.assertThat(userDetailsFound.getUsername()).isEqualTo("kaua_prado");
        Assertions.assertThat(userDetailsFound.getPassword()).isEqualTo("senha_criptografada");
        Assertions.assertThat(userDetailsFound.getAuthorities()).hasSize(2);
    }

    @Test
    @DisplayName("findByLogin retorna null quando o login não existe")
    void findByLogin_ReturnsNull_WhenLoginDoesNotExist() {

        User userToBeSaved = User.builder()
                .login("faker")
                .password("t1_senha")
                .role(RoleEnum.USER)
                .build();

        userRepository.save(userToBeSaved);


        UserDetails userDetailsFound = userRepository.findByLogin("chovy");


        Assertions.assertThat(userDetailsFound).isNull();
    }
}