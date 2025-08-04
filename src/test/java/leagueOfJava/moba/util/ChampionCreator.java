package leagueOfJava.moba.util;

import leagueOfJava.moba.domain.Champion;

public class ChampionCreator
{
    public static Champion createChampionToBeSaved(){
        return Champion.builder()
                .name("Malzahar")
                .ability("Creatures")
                .championType("Mage")
                .human(true)
                .active(true)
                .build();
    }

    public static Champion createValidChampion(){
        return Champion.builder()
                .id(1L)
                .name("Malzahar")
                .ability("Creatures")
                .championType("Mage")
                .human(true)
                .active(true)
                .build();
    }
}
