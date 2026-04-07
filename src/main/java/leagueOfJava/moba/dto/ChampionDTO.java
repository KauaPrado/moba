package leagueOfJava.moba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChampionDTO {
    private Long id;
    private String name;
    private String ability;
    private String championType;
    private boolean human;
    private boolean active;
}
