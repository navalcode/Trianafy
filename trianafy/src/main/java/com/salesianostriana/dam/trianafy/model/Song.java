package com.salesianostriana.dam.trianafy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    private String album;
    private String year;

    public Song(String title, Artist artist, String album, String year) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }
}
