package leagueOfJava.moba.service;

import leagueOfJava.moba.domain.Champion;
import leagueOfJava.moba.domain.ChampionStats;
import leagueOfJava.moba.dto.ChampionStatsDTO;
import leagueOfJava.moba.exception.ResultadoNaoEncontradoException;
import leagueOfJava.moba.repository.ChampionRepository;
import leagueOfJava.moba.repository.ChampionStatsRepository;
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
public class ChampionStatsService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ChampionStatsRepository championStatsRepository;
    @Autowired
    private ChampionRepository championRepository;

    @Transactional
    public void register(ChampionStatsDTO championStatsDTO){
        Optional<Champion> optionalChampion = championRepository.findById(championStatsDTO.getChampionID());
        if (optionalChampion.isEmpty()){
            throw new ResultadoNaoEncontradoException("Champion não encontrado para o id informado. ID{" + championStatsDTO.getChampionID() + "}");
        }

        ChampionStats championStats =  ChampionStats.builder()
                .name(championStatsDTO.getName())
                .win_rate(championStatsDTO.getWin_rate())
                .pick_rate(championStatsDTO.getPick_rate())
                .ban_rate(championStatsDTO.getBan_rate())
                .champion(optionalChampion.get())
                .build();
        championStatsRepository.save(championStats);
    }
    public Page<ChampionStatsDTO> getAll(Pageable pageable){
        Page<ChampionStats> championStatsPage= championStatsRepository.findAll(pageable);
        return championStatsPage.map(championStats -> modelMapper.map(championStats, ChampionStatsDTO.class));
    }

}
