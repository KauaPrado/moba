package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.dto.ChampionDTO;
import leagueOfJava.moba.exception.DadosInvalidosException;
import leagueOfJava.moba.exception.NomeJaExistenteException;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChampionRepository championRepository;

    @Transactional
    public void register(ChampionDTO championDTO) {
        if (championRepository.findByName(championDTO.getName().toLowerCase()).isPresent()) {
            throw new NomeJaExistenteException("o campeao {" + championDTO.getName() + "} já existe");
        }
        championRepository.save(modelMapper.map(championDTO, Champion.class));
    }

    public List<ChampionDTO> getAll() {
        return championRepository.findAll().stream().map(
                champion -> modelMapper.map(champion, ChampionDTO.class)
        ).collect(Collectors.toList());
    }

    public ChampionDTO findChampionByName(String name) {
        Optional<Champion> optionalChampion = championRepository.findByName(name.toLowerCase());

        if(optionalChampion.isEmpty()){
            throw new ResultadoNaoEncontradoException("Champion não encontrado para o nome informado");
        }

        return modelMapper.map(optionalChampion.get(), ChampionDTO.class);
    }

    public ChampionDTO findById(Long id) {
        Optional<Champion> optionalChampion = championRepository.findById(id);

        if (optionalChampion.isEmpty()) {
            throw new ResultadoNaoEncontradoException("Champion não encontrado para o id informado. ID{" + id + "}");
        }

        return modelMapper.map(optionalChampion.get(), ChampionDTO.class);
    }

    public void inactivate(Long id) {
        Optional<Champion> championToBeInactivated = championRepository.findById(id);
        if (championToBeInactivated.isEmpty()) {
            throw new ResultadoNaoEncontradoException("Champion não encontrado para o id informado. ID{" + id + "}");
        }

        if (!championToBeInactivated.get().isActive()) {
            throw new DadosInvalidosException(("o campeão já se encontra inativo"));
        }

        championToBeInactivated.get().setActive(false);

        championRepository.save(championToBeInactivated.get());
    }

    public void activate(Long id) {
        Optional<Champion> championToBeActivated = championRepository.findById(id);
        if (championToBeActivated.isEmpty()) {
            throw new ResultadoNaoEncontradoException("Champion não encontrado para o id informado. ID{" + id + "}");
        }

        if (championToBeActivated.get().isActive()) {
            throw new DadosInvalidosException(("o campeão já se encontra ativo"));
        }

        championToBeActivated.get().setActive(true);

        championRepository.save(championToBeActivated.get());
    }

    public void deleteByID(Long id) {
        this.findById(id);

        championRepository.deleteById(id);
    }
}