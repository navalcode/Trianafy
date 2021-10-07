package com.salesianostriana.dam.trianafy.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Lob
    private String description;

    @ManyToMany
    @ElementCollection
    private List<Song> songs;


    public Playlist(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
