package com.salesianostriana.dam.trianafy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.salesianostriana.dam.trianafy.repository.PlaylistRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistRepository repository;


    @Operation(summary = "Crea una playlist con los atributos previamente dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la playlist",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear correctamente la playlist por el ID",
                    content = @Content),
    })
    
    @PostMapping("/")
    public ResponseEntity<Playlist> create(@RequestBody Playlist nueva){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(nueva));
    }
}
