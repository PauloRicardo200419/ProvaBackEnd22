package br.com.tech4me.tech4music.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tech4me.tech4music.service.MusicaService;
import br.com.tech4me.tech4music.shared.MusicaDTO;
import br.com.tech4me.tech4music.view.model.MusicaResponseDTO;
import br.com.tech4me.tech4music.view.model.MusicaResponsePorIdDTO;

@RestController
@RequestMapping("/api/musicas")
public class MusicaController {

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private MusicaService servico;
    
    @GetMapping
    public ResponseEntity<List<MusicaResponseDTO>> obterMusica(){
        List<MusicaDTO> dto = servico.obterTodasAsMusicas();


        List<MusicaResponseDTO> musicas = dto.stream()
           .map(l -> mapper.map(l, MusicaResponseDTO.class))
           .collect(Collectors.toList());

        return new ResponseEntity<>(musicas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicaResponsePorIdDTO> obterMusicaPorId(@PathVariable String id){
        Optional<MusicaDTO> musica = servico.obterMusicaPorId(id);

        if(musica.isPresent()){
            return new ResponseEntity<>(mapper.map(musica.get(), MusicaResponsePorIdDTO.class), HttpStatus.FOUND);
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<MusicaDTO> cadastrarMusica(@RequestBody @Valid MusicaDTO musica){
        return new ResponseEntity<>(servico.armazenarMusica(musica), HttpStatus.CREATED);    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirMusica(@PathVariable String id){
        servico.deletarMusica(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicaDTO> atualizarMusica(@PathVariable String id, @RequestBody @Valid MusicaDTO musica){
        return new ResponseEntity<>(servico.atualizarMusica(id, musica), HttpStatus.ACCEPTED);
    }
}
