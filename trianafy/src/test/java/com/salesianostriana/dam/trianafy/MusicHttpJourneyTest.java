package com.salesianostriana.dam.trianafy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesianostriana.dam.trianafy.model.*;
import com.salesianostriana.dam.trianafy.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties="spring.datasource.url=jdbc:h2:mem:music_review;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
@AutoConfigureMockMvc
class MusicHttpJourneyTest {
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper json;
    @Autowired ArtistRepository artists;
    @Autowired SongRepository songs;
    @Autowired PlaylistRepository lists;
    Artist artist; Song song; Playlist list;
    final long missing = Long.MAX_VALUE;
    @BeforeEach void fixture() {
        artist=artists.save(new Artist("Fictional artist"));
        song=songs.save(new Song("Fictional song",artist,"Sample album","2026"));
        list=lists.save(new Playlist("Sample playlist","Fictional test data"));
    }
    void attach() throws Exception { mvc.perform(post("/lists/{p}/songs/{s}",list.getId(),song.getId())).andExpect(status().isOk()); }
    @Test void missingArtistDeleteIs404() throws Exception { mvc.perform(delete("/artist/{id}",missing)).andExpect(status().isNotFound()); }
    @Test void missingPlaylistDeleteIs404() throws Exception { mvc.perform(delete("/list/{id}",missing)).andExpect(status().isNotFound()); }
    @Test void missingSongDeleteIs404() throws Exception { mvc.perform(delete("/song/{id}",missing)).andExpect(status().isNotFound()); }
    @Test void missingPlaylistMembershipIs404() throws Exception {
        mvc.perform(get("/list/{p}/songs/{s}",missing,song.getId())).andExpect(status().isNotFound());
        mvc.perform(delete("/list/{p}/songs/{s}",missing,song.getId())).andExpect(status().isNotFound());
        mvc.perform(post("/lists/{p}/songs/{s}",missing,song.getId())).andExpect(status().isNotFound());
    }
    @Test void missingSongMembershipIs404() throws Exception {
        mvc.perform(get("/list/{p}/songs/{s}",list.getId(),missing)).andExpect(status().isNotFound());
        mvc.perform(delete("/list/{p}/songs/{s}",list.getId(),missing)).andExpect(status().isNotFound());
        mvc.perform(post("/lists/{p}/songs/{s}",list.getId(),missing)).andExpect(status().isNotFound());
    }
    @Test void absentMembershipReadAndRemoveDoNotWrite() throws Exception {
        mvc.perform(get("/list/{p}/songs/{s}",list.getId(),song.getId())).andExpect(status().isNotFound());
        mvc.perform(delete("/list/{p}/songs/{s}",list.getId(),song.getId())).andExpect(status().isNotFound());
        assertTrue(lists.findById(list.getId()).orElseThrow().getSongs().isEmpty());
    }
    @Test void addingTwiceKeepsOneMembership() throws Exception {
        attach(); attach();
        assertEquals(1,lists.findById(list.getId()).orElseThrow().getSongs().size());
        mvc.perform(get("/list/{p}/songs/{s}",list.getId(),song.getId())).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(song.getId()));
    }
    @Test void removalPreservesSong() throws Exception {
        attach(); mvc.perform(delete("/list/{p}/songs/{s}",list.getId(),song.getId())).andExpect(status().isNoContent());
        assertTrue(lists.findById(list.getId()).orElseThrow().getSongs().isEmpty());
        assertTrue(songs.existsById(song.getId()));
    }
    @Test void deletingArtistDetachesSongsEvenWithAnUnassignedSong() throws Exception {
        songs.save(new Song("Unassigned song","Sample","2026"));
        mvc.perform(delete("/artist/{id}",artist.getId())).andExpect(status().isNoContent());
        assertFalse(artists.existsById(artist.getId()));
        assertNull(songs.findById(song.getId()).orElseThrow().getArtist());
    }
    @Test void deletingSongDetachesEveryPlaylist() throws Exception {
        attach(); Playlist second=new Playlist("Second","Sample"); second.getSongs().add(song); second=lists.save(second);
        mvc.perform(delete("/song/{id}",song.getId())).andExpect(status().isNoContent());
        assertFalse(songs.existsById(song.getId()));
        assertTrue(lists.findById(list.getId()).orElseThrow().getSongs().isEmpty());
        assertTrue(lists.findById(second.getId()).orElseThrow().getSongs().isEmpty());
    }
    String body(long artistId) throws Exception { return json.writeValueAsString(Map.of("title","Changed","artistId",artistId,"album","Sample","year","2026")); }
    @Test void unknownArtistCreateDoesNotPersist() throws Exception {
        long before=songs.count(); mvc.perform(post("/song/").contentType("application/json").content(body(missing))).andExpect(status().isNotFound()); assertEquals(before,songs.count());
    }
    @Test void unknownArtistEditDoesNotMutate() throws Exception {
        mvc.perform(put("/song/{id}",song.getId()).contentType("application/json").content(body(missing))).andExpect(status().isNotFound());
        assertEquals("Fictional song",songs.findById(song.getId()).orElseThrow().getTitle());
    }
    @Test void suppliedArtistIdDoesNotOverwrite() throws Exception {
        mvc.perform(post("/artist/").contentType("application/json").content(json.writeValueAsString(Map.of("id",artist.getId(),"name","Overwrite")))).andExpect(status().isBadRequest());
        assertEquals("Fictional artist",artists.findById(artist.getId()).orElseThrow().getName());
    }
    @Test void validCreateAndEditKeepTheirPublicShapes() throws Exception {
        String response=mvc.perform(post("/song/").contentType("application/json").content(body(artist.getId()))).andExpect(status().isCreated()).andExpect(jsonPath("$.artist").value("Fictional artist")).andReturn().getResponse().getContentAsString();
        long id=json.readTree(response).get("id").asLong();
        mvc.perform(put("/song/{id}",id).contentType("application/json").content(body(artist.getId()))).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(id));
        mvc.perform(post("/lists").contentType("application/json").content(json.writeValueAsString(Map.of("name","Sample","description","Fictional")))).andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Sample"));
    }
    @Test void suppliedPlaylistIdDoesNotOverwrite() throws Exception {
        mvc.perform(post("/lists").contentType("application/json").content(json.writeValueAsString(Map.of("id",list.getId(),"name","Overwrite","description","Changed")))).andExpect(status().isBadRequest());
        assertEquals("Sample playlist",lists.findById(list.getId()).orElseThrow().getName());
    }
}
