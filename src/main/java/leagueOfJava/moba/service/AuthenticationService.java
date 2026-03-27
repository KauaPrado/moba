package leagueOfJava.moba.service;


import leagueOfJava.moba.domain.User;
import leagueOfJava.moba.dto.AuthenticationDto;
import leagueOfJava.moba.dto.RegisterDTO;
import leagueOfJava.moba.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void login(AuthenticationDto data) {
        var usernamePassword =
                new UsernamePasswordAuthenticationToken(data.login(), data.password());

        authenticationManager.authenticate(usernamePassword);
    }

    public void register(RegisterDTO registerDTO){
        User user = new User();
        user.setLogin(registerDTO.getLogin());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setRole(registerDTO.getRole());

        repository.save(user);
    }
}
