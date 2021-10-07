package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor @NoArgsConstructor
public class CreatePlaylistDto {

    private Long id;
    private String name;
    private String description;
    private int numberOfSongs;

}
