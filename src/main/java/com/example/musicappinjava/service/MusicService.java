package com.example.musicappinjava.service;

import com.example.musicappinjava.domain.Song;
import com.example.musicappinjava.repository.SongRepository;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MusicService {
    private final SongRepository repository;

    public MusicService(SongRepository repository){
        this.repository = repository;
    }

    private int generateID() {
        List<Song> allSongs = repository.findAll();
        return allSongs.stream()
                .mapToInt(Song::getId)
                .max()
                .orElse(0) + 1;
    }

    public List<Song> getAllSongs(){
        return repository.findAll();
    }

    public void addSong(String band, String title, String genre, String duration) throws Exception {
        if (band == null || band.isEmpty() || title == null || title.isEmpty() || genre == null || genre.isEmpty() || duration == null || duration.isEmpty()){
            throw new Exception("Datele melodiei sunt incomplete");
        }
        int id = generateID();
        Song newSong = new Song(id, band, title, genre, duration);
        repository.addSong(newSong);
    }

    public List<Song> generatePlaylist(List<Song> allSongs) throws Exception {
        List<Song> playlist = new ArrayList<>();
        List<Song> shuffled = new ArrayList<>(allSongs);
        Collections.shuffle(shuffled);

        int totalSeconds = 0;
        for (Song s : shuffled) {
            boolean isFirst = playlist.isEmpty();
            boolean diffBand = isFirst || !playlist.get(playlist.size() - 1).getBand().equals(s.getBand());
            boolean diffGenre = isFirst || !playlist.get(playlist.size() - 1).getGenre().equals(s.getGenre());

            if (diffBand && diffGenre){
                playlist.add(s);
                totalSeconds += s.getDurationInSecond();
            }
            if (playlist.size() >= 3 && totalSeconds > 900) {
                return playlist;
            }
        }
        throw new Exception("Nu s-a putut genera lista");
    }

    public void savePlaylist(String tableName, List<Song> playlist) throws Exception {
        if (tableName == null || tableName.trim().isEmpty()){
            throw new Exception("Numele nu poate fi gol");
        }
        repository.savePlaylist(tableName, playlist);
    }

    public List<String> getSavedPlaylistNames(){
        return repository.getPlaylistNames();
    }

    public List<Song> loadPlaylist(String name) {
        return repository.getSongsFromPlaylist(name);
    }

}
