package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {
    public Song createSongDtoToSong (CreateSongDto s){
        return new Song(
                s.getTitle(),
                s.getAlbum(),
                s.getYear()
        );
    }

    public GetSongDto SongToGetSongDto (Song s){
        GetSongDto result = new GetSongDto();

        result.setTitle(s.getTitle());
        result.setAlbum(s.getAlbum());
        result.setYear(s.getYear());
        result.setArtist(s.getArtist().getName());
        return result;
    }

    public SongDtoToUser conversorPostSong (Song s){
        SongDtoToUser result = new SongDtoToUser();

        result.setId(s.getId());
        result.setTitle(s.getTitle());
        result.setArtist(s.getArtist().getName());
        result.setAlbum(s.getAlbum());
        result.setYear(s.getYear());
        return result;
    }
}
