package leagueOfJava.moba.controller;


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
public class AuthenticatorController {

    @Autowired
    private AuthenticationService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        try {
            var usernamePassword =
                    new UsernamePasswordAuthenticationToken(data.login(), data.password());

            var auth = this.authenticationManager.authenticate(usernamePassword);

            var user = (User) auth.getPrincipal();

            var token = tokenService.generateToken(user);

            return ResponseEntity.ok(token);

        } catch (Exception e) {
            e.printStackTrace(); // 👈 aqui

            return ResponseEntity
                    .badRequest()
                    .body(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if (this.repository.findByLogin(data.getLogin()) != null) {
            return ResponseEntity.badRequest().build();
        }

        service.register(data);

        return ResponseEntity.ok().build();
    }
}