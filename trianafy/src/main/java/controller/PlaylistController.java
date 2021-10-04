package controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import model.Playlist;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class PlaylistController {

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
