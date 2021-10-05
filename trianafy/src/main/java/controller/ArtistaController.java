package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import model.Artista;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ArtistaRepository;

import java.util.List;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Esta clase implementa controladores REST para Artista")
public class ArtistaController {

    private final ArtistaRepository artistaRepository;

    @Operation(summary = "Obtiene un artista por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el artista",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artista.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el artista",
                    content = @Content),
    })

    @GetMapping("/{id}")
    public ResponseEntity<Artista> findOne(@Parameter("El id del artista a buscar")
                                                     @PathVariable Long id) {
        return ResponseEntity.of(artistaRepository.findById(id));

    }
}
