package controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import model.Playlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.PlaylistRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistRepository repository;

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> edit(
            @RequestBody Playlist p,
            @PathVariable Long id){
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
