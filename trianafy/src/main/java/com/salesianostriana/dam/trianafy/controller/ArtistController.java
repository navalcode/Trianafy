package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.salesianostriana.dam.trianafy.model.Artist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
@Tag(name = "Artist", description = "Esta clase implementa controladores REST para Artist")
public class ArtistController {

    private final ArtistRepository artistRepository;

    @Operation(summary = "Elimina un artist por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Artist Eliminado correctamente")})

    @DeleteMapping("/id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene un artist por su id y actualiza sus datos con el artist obtenido mediante " +
            "el cuerpo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el artist por el id y se ha modificado correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artist por su id",
                    content = @Content),
    })

    @PutMapping("/{id}")
    public ResponseEntity<Artist> edit(@RequestBody Artist e, @PathVariable Long id) {
        return ResponseEntity.of(
                artistRepository
                        .findById(id)
                        .map(a -> {
                            a.setName(e.getName());
                            artistRepository.save(a);
                            return a;
                        })
        );
    }

    @Operation(summary = "Obtiene un artist por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el artist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el artist",
                    content = @Content),
    })

    @GetMapping("/{id}")
    public ResponseEntity<Artist> findOne(@Parameter(description = "El id del artist a buscar")
                                                     @PathVariable Long id) {
        return ResponseEntity.of(artistRepository.findById(id));
    }

    @Operation(summary = "Crea un artist con el modelo obtenido del cuerpo de la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el artist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el artist, petición erronea.",
                    content = @Content),
    })

    @PostMapping("/")
    public ResponseEntity<Artist> create(@RequestBody Artist nuevoArtista) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistRepository.save(nuevoArtista));
    }

    @Operation(summary = "Obtiene todos los artists existentes en el repositorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los artists",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los artists",
                    content = @Content),
    })

    @GetMapping("/")
    public ResponseEntity<List<Artist>> findAll() {
        return ResponseEntity
                .ok()
                .body(artistRepository.findAll());


    }
}
