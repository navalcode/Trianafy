package controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.ArtistaRepository;

@RestController
@RequestMapping("/artist")
@RequiredArgsConstructor
@Tag(name = "Artista", description = "Esta clase implementa controladores REST para Artista")
public class ArtistaController {

    private final ArtistaRepository artistaRepository;

    @Operation(summary = "Elimina un artista por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Artista Eliminado correctamente")})

    @DeleteMapping("/id")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        artistaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
