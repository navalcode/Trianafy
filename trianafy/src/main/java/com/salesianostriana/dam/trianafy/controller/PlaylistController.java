package com.salesianostriana.dam.trianafy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import com.salesianostriana.dam.trianafy.model.Playlist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salesianostriana.dam.trianafy.repository.PlaylistRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistRepository repository;


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

    @GetMapping("/")
    public ResponseEntity<List<Playlist>> findAll(){
        return ResponseEntity.ok().body(repository.findAll());

    }
}
