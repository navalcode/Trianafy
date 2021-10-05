package controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import model.Playlist;

import org.springframework.http.ResponseEntity;
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


    @GetMapping("/")
    public ResponseEntity<List<Playlist>> findAll(){
        return ResponseEntity.ok().body(repository.findAll());

    @PostMapping("/")
    public ResponseEntity<Playlist> create(@RequestBody Playlist nueva){
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(nueva));
    }
}
