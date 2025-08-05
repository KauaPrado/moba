package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.domain.Skin;
import leagueOfJava.moba.dto.SkinDTO;
import leagueOfJava.moba.exception.DadosInvalidosException;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.repository.SkinRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void register(SkinDTO skinDTO) {
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

    public Page<SkinDTO> getAll(Pageable pageable){
        Page<Skin> skinPage = skinRepository.findAll(pageable);
        return skinPage.map(skin -> modelMapper.map(skin, SkinDTO.class));
    }

    public SkinDTO findByID(Long id){
        Optional<Skin> optionalSkin = skinRepository.findById(id);
        if (optionalSkin.isEmpty()){
            throw new ResultadoNaoEncontradoException("Skin não encontrada para o id informado. ID{" + id + "}");
        }
        return modelMapper.map(optionalSkin.get(), SkinDTO.class);
    }

    public void inactivate(Long id){
        Optional<Skin> skinToBeInactivated = skinRepository.findById(id);
        if (skinToBeInactivated.isEmpty()) {
            throw new ResultadoNaoEncontradoException("skin não encontrada para o id informado. ID{" + id + "}");
        }
        if (!skinToBeInactivated.get().isActive()) {
            throw new DadosInvalidosException(("a skin já se encontra inativa"));
        }
        skinToBeInactivated.get().setActive(false);
        skinRepository.save(skinToBeInactivated.get());
    }

    public void activate(Long id) {
        Optional<Skin> skinToBeActivated = skinRepository.findById(id);
        if (skinToBeActivated.isEmpty()) {
            throw new ResultadoNaoEncontradoException("skin não encontrada para o id informado. ID{" + id + "}");
        }

        if (skinToBeActivated.get().isActive()) {
            throw new DadosInvalidosException(("a skin já se encontra ativa"));
        }

        skinToBeActivated.get().setActive(true);

        skinRepository.save(skinToBeActivated.get());
    }

}
