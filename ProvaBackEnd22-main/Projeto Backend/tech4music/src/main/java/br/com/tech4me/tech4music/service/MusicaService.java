package br.com.tech4me.tech4music.service;

import java.util.List;
import java.util.Optional;

import br.com.tech4me.tech4music.shared.MusicaDTO;

public interface MusicaService {
    List<MusicaDTO> obterTodasAsMusicas();

    Optional<MusicaDTO> obterMusicaPorId(String id);

    MusicaDTO armazenarMusica(MusicaDTO music);

    void deletarMusica(String id);

    MusicaDTO atualizarMusica(String id, MusicaDTO music);
    
}
