package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.CreatePlaylistDto;
import com.salesianostriana.dam.trianafy.dto.GetPlaylistDto;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.dto.CreatePlaylistDto;
import com.salesianostriana.dam.trianafy.dto.PlaylistDtoConverter;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repository.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repository.SongRepository;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.salesianostriana.dam.trianafy.model.Playlist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@Tag(name = "Playlist", description = "El controlador de las playlist")
public class PlaylistController {

    private final PlaylistRepository repository;
    private final PlaylistDtoConverter dtoConverter;
    private final SongRepository sRepository;


    @Operation(summary = "Eliminar una lista por el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No devuelve nada, sólo borra la playlist indicada.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))})
    })
    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar una playlist existente por el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido modificar la playlist.",
                    content = @Content),
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<Playlist> edit(
            @RequestBody Playlist p,
            @PathVariable Long id) {
        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(
                repository.findById(id).map(m -> {
                    m.setName(p.getName());
                    m.setDescription(p.getDescription());
                    repository.save(m);
                    return m;
                })
        );
    }

    @Operation(summary = "Buscar una playlist por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Playlist",
                    content = {@Content(mediaType = "applicacion/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la Playlist por el ID",
                    content = @Content),
    })
    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findOne(
            @Parameter(description = "ID de la Playlist que desea buscar")
            @PathVariable Long id
    ) {
        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .body(repository.findById(id).orElse(null));
    }

    @Operation(summary = "Ver todas las listas de reproducción existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se listan todas las listas de reprodución",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
    })
    @GetMapping("/lists")
    public ResponseEntity<List<GetPlaylistDto>> findAll() {
        List<Playlist> data = repository.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            List<GetPlaylistDto> result = data.stream().map(dtoConverter::playlistToGetPlaylistDto).collect(Collectors.toList());
            return ResponseEntity.ok().body(result);
        }
    }

    @Operation(summary = "Crea una playlist con los atributos previamente dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear correctamente la playlist",
                    content = @Content),
    })
    @PostMapping("/lists")
    public ResponseEntity<CreatePlaylistDto> create(@RequestBody CreatePlaylistDto dto) {

        Playlist nueva = dtoConverter.createPlaylistDtoToPlaylist(dto);

        repository.save(nueva);

        CreatePlaylistDto dtoMostrar = dtoConverter.playlistToGetPlaylistDtoToCreatePlaylist(nueva);

        return ResponseEntity.status(HttpStatus.CREATED).body(dtoMostrar);
    }

    @Operation(summary = "Mostrar todas las canciones de una playlist.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de todas las canciones de la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content)
    })
    @GetMapping("lists/{id}/songs")
    public ResponseEntity<Playlist> findAllSongsInPlaylist(
            @Parameter(description = "ID de la Playlist que desea buscar")
            @PathVariable Long id) {
        if (repository.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity
                .ok()
                .body(repository.findById(id).orElse(null));
    }

    @Operation(summary = "Agrega una canción existente a una playlist existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha agregado una canción a la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción.",
                    content = @Content),
    })
    @PostMapping("/lists/{id1}/songs/{id2}")
    public ResponseEntity<Playlist> addSong(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Playlist> l = repository.findById(id1);
        Optional<Song> s = sRepository.findById(id2);

        Playlist playlist;
        Song song;

        if (l.isEmpty() || s.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            playlist = l.get();
            song = s.get();
        }

        playlist.getSongs().add(song);
        return ResponseEntity.ok(repository.save(playlist));

    }

    @Operation(summary = "Borra una canción de una lista.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la canción de la lista.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content)
    })
    @DeleteMapping("list/{id1}/songs/{id2}")
    public ResponseEntity<Song> DeleteOneSong(@PathVariable Long id1,
                                              @PathVariable Long id2) {

        if (repository.findById(id1) != null) {
            Playlist listaNueva = repository.findById(id1).get();
            listaNueva.getSongs().remove(sRepository.findById(id2).get());

            repository.save(listaNueva);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Muestra una canción si existe dentro de una lista")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Muestra la canción existente en la lista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la lista",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción en la lista",
                    content = @Content),
    })
    @GetMapping("list/{id1}/songs/{id2}")
    public ResponseEntity<Song> findOneSong(
            @Parameter(description = "ID de la Playlist que desea buscar")
            @PathVariable Long id1,
            @PathVariable Long id2
    ) {
        Playlist listaCanciones = repository.findById(id1).get();

        Song sComprobar = sRepository.findById(id2).get();

        if (listaCanciones != null && listaCanciones.getSongs().contains(sComprobar) && sComprobar != null) {

            return ResponseEntity
                    .ok().body(sComprobar);

        }
        return ResponseEntity.notFound().build();
    }

}
