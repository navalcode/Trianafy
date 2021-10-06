package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Playlist;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.salesianostriana.dam.trianafy.repository.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistRepository repository;
    private final SongRepository SongRepository;


    @Operation(summary = "Ver una canción por el ID de una playlist también buscada por el ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Devuelve una playlist con la canción indicada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist o ninguna canción.",
                    content = @Content),
    })

    @GetMapping("/{id}/songs/{id2}")
    public ResponseEntity<Playlist> findOne(
            @Parameter(description = "ID de la Playlist que desea buscar")
            @PathVariable Long id, Long id2
    ) {
        return ResponseEntity
                .ok()
                .body(repository.findById(id).orElse(null).SongRepository.findById(id2));
    }

}
