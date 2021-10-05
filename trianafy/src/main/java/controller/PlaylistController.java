package controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import model.Playlist;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    /*@ApiResponse(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la Playlist",
                    content = { @Content(mediaType = "applicacion/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha encontrado la Playlist por el ID",
                    content = @Content),
    })*/
    @GetMapping("/{id}")
    public ResponseEntity<Playlist> findOne(
            @Parameter(description = "ID de la Playlist que desea buscar")
            @PathVariable Long id
    ){
        return ResponseEntity
                .ok()
                .body(repository.findById(id).orElse(null));



    @GetMapping("/")
    public ResponseEntity<List<Playlist>> findAll(){
        return ResponseEntity.ok().body(repository.findAll());

    @PostMapping("/")
    public ResponseEntity<Playlist> create(@RequestBody Playlist nueva){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(nueva));


    }

}
