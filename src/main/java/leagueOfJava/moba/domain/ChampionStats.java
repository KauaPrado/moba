package leagueOfJava.moba.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tb_champions_stats")
public class ChampionStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String win_rate;
    private String pick_rate;
    private String ban_rate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "champion_id", referencedColumnName = "id")
    private Champion champion;

}
