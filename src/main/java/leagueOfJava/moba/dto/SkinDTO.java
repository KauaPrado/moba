package leagueOfJava.moba.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkinDTO {
    private String name;
    private Long championID;
    private boolean active;
}
