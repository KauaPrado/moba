package leagueOfJava.moba.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import leagueOfJava.moba.dto.ChampionDTO;
import leagueOfJava.moba.dto.SkinDTO;
import leagueOfJava.moba.service.SkinService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("skins")
public class SkinController {

    @Autowired
    private SkinService service;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody SkinDTO skinDTO){
        service.register(skinDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(description = "retorna todas as skins")
    @ApiResponse(responseCode = "200", description = "Retorna todas as skins")
    @GetMapping
    public ResponseEntity<Page<SkinDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }
    @Operation(description = "retorna a skin com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorna o campeão"), @ApiResponse(responseCode = "400", description = "não existe a skin com o id informado")})
    @GetMapping(path = "/{id}")
    public ResponseEntity<SkinDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByID(id));
    }

    @Operation(description = "desativa a skin com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "skin desativada"), @ApiResponse(responseCode = "400", description = "id inválido")})
    @PutMapping(path = "inactivate/{id}")
    public ResponseEntity<Void> inactivate(@PathVariable Long id) {
        service.inactivate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Operation(description = "ativa a skin com base no id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "skin"), @ApiResponse(responseCode = "400", description = "id inválido")})
    @PutMapping(path = "activate/{id}")
    public ResponseEntity<Void> activate(@PathVariable Long id) throws BadRequestException {
        service.activate(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
