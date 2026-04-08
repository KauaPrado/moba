package leagueOfJava.moba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SkinDTO {
    private String name;
    private Long championID;
    private boolean active;
}
