package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import model.Artista;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ArtistaRepository;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Esta clase implementa controladores REST para Artista")
public class ArtistaController {

    private final ArtistaRepository artistaRepository;

    @Operation(summary = "Obtiene un artista por su id y actualiza sus datos con el artista obtenido mediante " +
            "el cuerpo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el artista por el id y se ha modificado correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por su id",
                    content = @Content),
    })

    @PutMapping("/{id}")
    public ResponseEntity<Artista> edit(@RequestBody Artista editArtista, @PathVariable Long id) {
        return ResponseEntity.of(
                artistaRepository
                        .findById(id)
                        .map(a -> {
                            a.setName(editArtista.getName());
                            artistaRepository.save(editArtista);
                            return editArtista;
                        })
        );
    }
}
