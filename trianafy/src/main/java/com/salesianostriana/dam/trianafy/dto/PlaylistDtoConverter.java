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

    public PlaylistDto playlistToGetPlaylistDto(Playlist p){
        PlaylistDto result = new PlaylistDto();
        result.setId(p.getId());
        result.setName(p.getName());
        result.setDescription(p.getDescription());
        return result;
    }
}
