package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import model.Artista;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ArtistaRepository;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Esta clase implementa controladores REST para Artista")
public class ArtistaController {

    private final ArtistaRepository artistaRepository;

    @Operation(summary = "Crea un artista con el modelo obtenido del cuerpo de la petición.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear el artista, petición erronea.",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Artista> crate(@RequestBody Artista nuevoArtista) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(artistaRepository.save(nuevoArtista));
    }

    @Operation(summary = "Obtiene todos los artistas existentes en el repositorio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los artistas",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los artistas",
                    content = @Content),
    })

    @GetMapping("/")
    public ResponseEntity<List<Artista>> findAll() {
        return ResponseEntity
                .ok()
                .body(artistaRepository.findAll());
    }
}
