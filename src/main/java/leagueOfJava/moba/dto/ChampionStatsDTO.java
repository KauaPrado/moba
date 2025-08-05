package leagueOfJava.moba.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChampionStatsDTO {
    private String name;
    private String win_rate;
    private String pick_rate;
    private String ban_rate;
}
