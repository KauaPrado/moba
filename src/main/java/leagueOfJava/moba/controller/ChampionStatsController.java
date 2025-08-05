package leagueOfJava.moba.controller;

import leagueOfJava.moba.dto.ChampionStatsDTO;
import leagueOfJava.moba.service.ChampionStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("championStats")
public class ChampionStatsController {
    @Autowired
    private ChampionStatsService service;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody ChampionStatsDTO championStatsDTO){
        service.register(championStatsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
