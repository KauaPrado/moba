package leagueOfJava.moba.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import leagueOfJava.moba.domain.User;
import leagueOfJava.moba.dto.AuthenticationDto;
import leagueOfJava.moba.dto.RegisterDTO;
import leagueOfJava.moba.repository.UserRepository;
import leagueOfJava.moba.service.AuthenticationService;
import leagueOfJava.moba.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthenticatorController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;


    @Operation(description = "Efetua o login do usuário e retorna o token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Credenciais inválidas ou usuário inexistente")
    })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){

        var usernamePassword =  new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);
        return ResponseEntity.ok(token);

    }


    @Operation(description = "Registra um novo usuário (invocador) no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou login já em uso")
    })
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (this.repository.findByLogin(data.getLogin()) != null) {
            return ResponseEntity.badRequest().build();
        }

        service.register(data);

        return ResponseEntity.ok().build();
    }
}