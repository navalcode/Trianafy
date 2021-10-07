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

        if(repository.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {

            return ResponseEntity
                    .ok()
                    .body(repository.findAll());
        }
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
    public ResponseEntity<SongDtoToUser> create(@RequestBody CreateSongDto dto) {

        if (dto.getArtistId() == null) {
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
    public ResponseEntity<SongDtoToUser> edit(@RequestBody CreateSongDto dto, @PathVariable Long id) {

        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Song edit = dtoConverter.createSongDtoToSong(dto);
            Artist artist = aRepository.findById(dto.getArtistId()).orElse(null);

            edit.setArtist(artist);

            return ResponseEntity.of(
                    repository.findById(id).map(s -> {
                        s.setArtist(edit.getArtist());
                        s.setAlbum(edit.getAlbum());
                        s.setTitle(edit.getTitle());
                        s.setYear(edit.getYear());
                        repository.save(s);
                        SongDtoToUser dtoMostrar = dtoConverter.conversorPostSong(repository.getById(id));
                        return dtoMostrar;
                    }));

        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
