package com.salesianostriana.dam.trianafy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import com.salesianostriana.dam.trianafy.model.Playlist;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.salesianostriana.dam.trianafy.repository.PlaylistRepository;


@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistRepository repository;


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
    @PutMapping("/{id}")
    public ResponseEntity<Playlist> edit(
            @RequestBody Playlist p,
            @PathVariable Long id) {
        return ResponseEntity.of(
                repository.findById(id).map(m -> {
                    m.setNombre(p.getNombre());
                    m.setDescripcion(p.getDescripcion());
                    repository.save(m);
                    return m;
                })
        );
    }
}
