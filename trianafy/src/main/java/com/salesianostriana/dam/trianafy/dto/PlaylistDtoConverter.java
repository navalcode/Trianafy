package com.salesianostriana.dam.trianafy.dto;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class PlaylistDtoConverter {

    public Playlist createPlaylistDtoToPlaylist(CreatePlaylistDto c){
        return new Playlist(
                c.getId(),
                c.getName(),
                c.getDescription()
        );
    }

    public GetPlaylistDto playlistToGetPlaylistDto(Playlist p){
        int numCanciones = p.getSongs().size();
        GetPlaylistDto result = new GetPlaylistDto();
        result.setId(p.getId());
        result.setName(p.getName());
        result.setNumberOfSongs(numCanciones);
        return result;
    }

    public GetPlaylistDto playlistToGetPlaylistDto2(Playlist p){
        GetPlaylistDto result = new GetPlaylistDto();
        result.setId(p.getId());
        result.setName(p.getName());
        result.setDescription(p.getDescription());
        return result;
    }
}
