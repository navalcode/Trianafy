package com.salesianostriana.dam.trianafy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSongDto {
    private String title;
    private Long artistId;
    private String album;
    private String year;

}
