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
        GetPlaylistDto result = new GetPlaylistDto();
        result.setName(p.getName());
        return result;
    }
}
