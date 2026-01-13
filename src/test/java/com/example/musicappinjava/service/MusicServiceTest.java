package com.example.musicappinjava.service;

import com.example.musicappinjava.domain.Song;
import com.example.musicappinjava.repository.SongRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MusicServiceTest {

    private MusicService musicService;

    @BeforeEach
    void setUp(){
        musicService = new MusicService(new SongRepository());
    }

    @Test
    void generatePlaylistSucces() throws Exception{
        List<Song> pieseDisponibile = Arrays.asList(
                new Song(1, "Coldplay", "Yellow", "Alternative", "06:00"),
                new Song(2, "Radiohead", "Creep", "Rock", "06:00"),
                new Song(3, "Muse", "Uprising", "Synth-Rock", "04:00")
        );
        List<Song> playlist = musicService.generatePlaylist(pieseDisponibile);
        assertNotNull(playlist);
        assertTrue(playlist.size() >= 3, "Playlist-ul trebuie să aibă cel puțin 3 piese.");
        int durataTotala = playlist.stream().mapToInt(Song::getDurationInSecond).sum();
        assertTrue(durataTotala > 900, "Durata totală trebuie să depășească 15 minute (900s).");

        for (int i = 0; i < playlist.size() - 1; i++) {
            Song curenta = playlist.get(i);
            Song urmatoarea = playlist.get(i + 1);

            assertNotEquals(curenta.getBand(), urmatoarea.getBand(),
                    "Nu pot exista două piese consecutive de la aceeași formație.");
            assertNotEquals(curenta.getGenre(), urmatoarea.getGenre(),
                    "Nu pot exista două piese consecutive de același gen.");
        }
    }
    @Test
    void generatePlaylistFail() throws Exception {
        List<Song> piesePutine = Arrays.asList(
                new Song(1, "Coldplay", "Yellow", "Alternative", "05:00"),
                new Song(2, "Radiohead", "Creep", "Rock", "05:00")
        );

        Exception exception = assertThrows(Exception.class, () -> {
            musicService.generatePlaylist(piesePutine);
        });

        assertEquals("Nu s-a putut genera lista", exception.getMessage());
    }
}