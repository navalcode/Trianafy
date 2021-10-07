package com.salesianostriana.dam.trianafy.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Song> songs = new ArrayList<>();


    public Playlist(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
