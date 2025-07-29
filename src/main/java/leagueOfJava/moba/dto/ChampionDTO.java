package leagueOfJava.moba.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChampionDTO {
    private String name;
    private String ability;
    private String championType;
    private boolean human;
    private boolean active;
}
