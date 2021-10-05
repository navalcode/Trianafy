package model;


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

    private String nombre;

    @Lob
    private String descripcion;

    @ManyToMany
    @ElementCollection
    private List<Song> canciones;



}
