package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.SongDtoToUser;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import com.salesianostriana.dam.trianafy.model.Song;

import com.salesianostriana.dam.trianafy.repository.SongRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
@Tag(name = "Song", description = "El controlador de canciones")
public class SongController {

    private final SongRepository repository;
    private final ArtistRepository aRepository;
    private final ArtistController aController;
    private final SongDtoConverter dtoConverter;

    @GetMapping("/")
    public ResponseEntity<List<Song>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Song> findOne(
            @Parameter(description = "ID de la canción a buscar")
            @PathVariable Long id
    ) {

        return ResponseEntity
                .of(repository.findById(id));

    }


    @PostMapping("/")
    public ResponseEntity<SongDtoToUser> create (@RequestBody CreateSongDto dto){

            if (dto.getArtistId()== null){
                return ResponseEntity.notFound().build();
            }

            Song nueva = dtoConverter.createSongDtoToSong(dto);

            Artist artist = aRepository.findById(dto.getArtistId()).orElse(null);

            nueva.setArtist(artist);
            repository.save(nueva);

            SongDtoToUser dtoMostrar = dtoConverter.conversorPostSong(nueva);


            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(dtoMostrar);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Song> edit(
            @RequestBody Song s,
            @PathVariable Long id) {

        return ResponseEntity.of(
                repository.findById(id).map(c -> {
                    c.setTitle(s.getTitle());
                    c.setAlbum(s.getAlbum());
                    c.setArtist(s.getArtist());
                    c.setYear(s.getYear());
                    repository.save(c);
                    return c;
                })
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
