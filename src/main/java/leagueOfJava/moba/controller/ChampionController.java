package leagueOfJava.moba.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leagueOfJava.moba.dto.ChampionDTO;
import leagueOfJava.moba.service.ChampionService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("champions")
public class ChampionController {

    @Autowired
    private ChampionService service;

    @Operation(description = "retorna todos os campeões")
    @ApiResponse(responseCode = "200", description = "Retorna todos os campeões")
    @GetMapping
    public ResponseEntity<Page<ChampionDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @Operation(description = "retorna o campeão com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna o campeão"), @ApiResponse(responseCode = "400", description = "não existe o campeão com o id informado")})
    @GetMapping(path = "/{id}")
    public ResponseEntity<ChampionDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(description = "retorna o campeão com base no nome")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna o campeão"), @ApiResponse(responseCode = "400", description = "não existe o campeão com o nome informado")})
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<ChampionDTO> findAllByChampionName(@PathVariable String name) {
        return ResponseEntity.ok(service.findChampionByName(name));
    }

    @Operation(description = "insere o campeão com base no documento informado")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "campeão inserido"), @ApiResponse(responseCode = "400", description = "documento inválido")})
    @PostMapping
    public ResponseEntity<Void> register(@RequestBody ChampionDTO championDTO) {
        service.register(championDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "desativa o campeão com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "campeão desativado"), @ApiResponse(responseCode = "400", description = "id inválido")})
    @PutMapping(path = "inactivate/{id}")
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        service.inactivate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(description = "ativa o campeão com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "campeão ativado"), @ApiResponse(responseCode = "400", description = "id inválido")})
    @PutMapping(path = "activate/{id}")
    public ResponseEntity<Void> activate(@PathVariable Long id) throws BadRequestException {
        service.activate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(description = "deleta o campeão com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "campeão ativado"), @ApiResponse(responseCode = "400", description = "id inválido")})
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteByID(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
