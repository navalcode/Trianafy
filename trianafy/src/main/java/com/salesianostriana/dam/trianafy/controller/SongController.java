package com.salesianostriana.dam.trianafy.controller;


import com.salesianostriana.dam.trianafy.dto.CreateSongDto;
import com.salesianostriana.dam.trianafy.dto.SongDtoConverter;
import com.salesianostriana.dam.trianafy.dto.SongDtoToUser;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import com.salesianostriana.dam.trianafy.model.Song;

import com.salesianostriana.dam.trianafy.repository.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
@Tag(name = "Song", description = "El controlador de canciones")
public class SongController {

    private final SongRepository repository;
    private final ArtistRepository aRepository;
    private final SongDtoConverter dtoConverter;

    @Operation(summary = "Muestra todas las canciones existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista todas las canciones.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado canciones",
                    content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<SongDtoToUser>> findAll() {
        List<Song> data = repository.findAll();

        if(data.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            List<SongDtoToUser> result = data.stream().map(dtoConverter::conversorPostSong).collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }
    }

    @Operation(summary = "Muestra una canción por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Muestra la canción con el id solicitado",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado la canción",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<Song> findOne(
            @Parameter(description = "ID de la canción a buscar")
            @PathVariable Long id
    ) {
        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .of(repository.findById(id));

    }

    @Operation(summary = "Crea una canción agregando un artista a esta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado un artista con el id solicitado",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear la playlist",
                    content = @Content),
    })
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

    @Operation(summary = "Modifica una canción dado su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción.",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la canción.",
                    content = @Content),
    })
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

    @Operation(summary = "Elimina una canción por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado la canción.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
