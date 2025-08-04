package leagueOfJava.moba.controller;


import leagueOfJava.moba.dto.SkinDTO;
import leagueOfJava.moba.service.SkinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
