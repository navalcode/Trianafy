package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import com.salesianostriana.dam.trianafy.repository.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
@Tag(name = "Song", description = "El controlador de canciones")
public class SongController {

    private final SongRepository repository;
    private final ArtistRepository aRepository;
    private final ArtistController aController;

    @GetMapping("/")
    public ResponseEntity<List<Song>> findAll() {

        return ResponseEntity
                .ok()
                .body(repository.findAll());
    }

    @Operation(summary = "Obtiene una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la canción con ese ID",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Song> findOne(
            @Parameter(description = "ID de la canción a buscar")
            @PathVariable Long id
    ) {

        return ResponseEntity
                .of(repository.findById(id));

    }

    @PostMapping("/")
    public ResponseEntity<Song> create(@RequestBody Song nuevaCancion) {
        Artist nuevoArtista= nuevaCancion.getArtist();

        if (nuevaCancion.getTitle() == null || nuevaCancion.getAlbum() == null || nuevaCancion.getYear() == null) {
            return ResponseEntity.badRequest().build();
        }else {
            if (nuevoArtista!=null) {
                if(nuevoArtista.getId()==null) {
                    aController.create(nuevoArtista);
                }else {
                    if(!aRepository.existsById(nuevoArtista.getId()))
                        aRepository.save(nuevaCancion.getArtist());
                }
            }
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(repository.save(nuevaCancion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Song> edit(
            @RequestBody Song s,
            @PathVariable Long id) {

        return ResponseEntity.of(
                repository.findById(id).map(c -> {
                    c.setTitle(s.getTitle());
                    c.setAlbum(s.getAlbum());
                    c.setArtist(s.getArtist());
                    c.setYear(s.getYear());
                    repository.save(c);
                    return c;
                })
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
