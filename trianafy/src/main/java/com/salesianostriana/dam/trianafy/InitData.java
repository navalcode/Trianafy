package com.salesianostriana.dam.trianafy;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repository.ArtistRepository;
import com.salesianostriana.dam.trianafy.repository.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitData {

    private final ArtistRepository aRepository;
    private final SongRepository sRepository;
    private final PlaylistRepository pRepository;

    @PostConstruct
    public void init() {
        aRepository.saveAll(
                Arrays.asList(
                        new Artist("Mago de Oz"),
                        new Artist("Imagine Dragons")
                )
        );
        sRepository.saveAll(
                Arrays.asList(
                        new Song("Fiesta Pagana", aRepository.getById(1L), "Finisterra", "2000"),
                        new Song("Natural", aRepository.getById(2L), "Origins", "2018"),
                        new Song("Demons", aRepository.getById(2L), "Night Visions", "2012")
                )
        );
        pRepository.saveAll(
                Arrays.asList(
                        new Playlist("A tope", "Canciones motivacionales para despertarse con energía", List.of((sRepository.getById(2L)), (sRepository.getById(1L)) )),
                        new Playlist("De baño", "Canciones para la ducha",List.of((sRepository.getById(3L))) )
                )
        );
    }
}
