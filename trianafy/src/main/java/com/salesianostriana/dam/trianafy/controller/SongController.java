package com.salesianostriana.dam.trianafy.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/song")
@RequiredArgsConstructor
@Tag(name = "Song", description = "El controlador de canciones")
public class SongController {

}
