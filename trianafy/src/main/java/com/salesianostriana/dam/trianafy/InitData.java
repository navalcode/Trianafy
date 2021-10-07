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
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final PlaylistRepository playlistRepository;

    @PostConstruct
    public void init() {

        Artist a = new Artist("Muse");
        Artist b = new Artist("Queen");
        Artist c = new Artist("Bon Jovi");
        Artist d = new Artist("Abba");

        artistRepository.save(a);
        artistRepository.save(b);
        artistRepository.save(c);
        artistRepository.save(d);

        Song e = new Song("Panic Station",a,"The Second Law","2012");
        Song f = new Song("Supremacy",a,"The Second Law","2012");
        Song g = new Song("Bohemian Rhapsody",b,"A Night at the Opera","1975");
        Song h = new Song("You're My Best Friend",b,"A Night at the Opera","1975");
        Song i = new Song("Livin' On A Prayer",c,"Slippery When Wet","1986");
        Song j = new Song("You Give Love A Bad Name",c,"Slippery When Wet","1986");
        Song k = new Song("When I Kissed the Teacher",d,"Arrival","1976");
        Song l = new Song("Dancing Queen",d,"Arrival","1976");

        songRepository.save(e);
        songRepository.save(f);
        songRepository.save(g);
        songRepository.save(h);
        songRepository.save(i);
        songRepository.save(j);
        songRepository.save(k);
        songRepository.save(l);

        Playlist m = new Playlist("Nostalgia","Vuelve a sentir la emoción de aquellos años");
        m.getSongs().addAll(List.of(l,j,g));

        Playlist n = new Playlist("¡Marcha!","Hora de mover el esqueleto");
        n.getSongs().addAll(List.of(i,e,f));

        playlistRepository.save(m);
        playlistRepository.save(n);


    }
}
