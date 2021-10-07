package com.salesianostriana.dam.trianafy.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Song implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    private String album;
    private String year;


}
