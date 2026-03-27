package leagueOfJava.moba.dto;

import leagueOfJava.moba.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String login;
    private String password;
    private RoleEnum role;
}
