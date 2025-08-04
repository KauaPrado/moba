package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.domain.Skin;
import leagueOfJava.moba.dto.SkinDTO;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkinService {

    @Autowired
    ChampionRepository championRepository;

    @Autowired
    SkinRepository skinRepository;
    @Autowired
    ModelMapper modelMapper;

    public void register(SkinDTO skinDTO)
    {
        Optional<Champion> optionalChampion = championRepository.findById(skinDTO.getChampionID());

        if (optionalChampion.isEmpty()) {
            throw new ResultadoNaoEncontradoException("Champion não encontrado para o id informado. ID{" + skinDTO.getChampionID() + "}");
        }

        Skin skin = new Skin();
        skin.setName(skinDTO.getName());
        skin.setActive(skinDTO.isActive());
        skin.setChampion(optionalChampion.get());
        skinRepository.save(skin);
    }

}
