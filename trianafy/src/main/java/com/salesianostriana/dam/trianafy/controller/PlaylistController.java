package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.repository.PlaylistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository repository;


    @Operation(summary = "Eliminar una lista por el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "No devuelve nada, sólo borra la playlist indicada.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
    })

    @DeleteMapping("/list/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Modificar una playlist existente por el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de todas las playlists:",
                    content = { @Content(mediaType = "application/json",
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
                    content = { @Content(mediaType = "applicacion/json",
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
        return ResponseEntity
                .ok()
                .body(repository.findById(id).orElse(null));
    }

    @Operation(summary = "Ver todas las listas de reproducción existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Lista de todas las playlists:",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist.",
                    content = @Content),
    })
    @GetMapping("/lists")
    public ResponseEntity<List<Playlist>> findAll() {
        return ResponseEntity.ok().body(repository.findAll());
    }

    @Operation(summary = "Crea una playlist con los atributos previamente dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear correctamente la playlist",
                    content = @Content),
    })
    @PostMapping("/lists")
    public ResponseEntity<Playlist> create(@RequestBody Playlist nueva){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(nueva));
    }

}
