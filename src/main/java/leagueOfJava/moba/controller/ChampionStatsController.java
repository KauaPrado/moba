package leagueOfJava.moba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import leagueOfJava.moba.dto.ChampionDTO;
import leagueOfJava.moba.dto.ChampionStatsDTO;
import leagueOfJava.moba.service.ChampionStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @Operation(description = "retorna todas as estatisticas")
    @ApiResponse(responseCode = "200", description = "Retorna todas as estatisticas")
    @GetMapping
    public ResponseEntity<Page<ChampionStatsDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }
}
