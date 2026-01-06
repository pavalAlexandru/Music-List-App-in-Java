package com.example.musicappinjava.domain;

import java.time.LocalTime;

public class Song{
    private int id;
    private String band;
    private String title;
    private String genre;
    private LocalTime duration;

    public Song(int id, String band, String title, String genre, int minutes, int seconds){
        this.id = id;
        this.band = band;
        this.title = title;
        this.genre = genre;
        this.duration = LocalTime.of(0, minutes, seconds);
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalTime getDurationInSeconds() {
        return duration;
    }

    public void setDurationInSeconds(int minutes, int seconds) {
        this.duration = LocalTime.of(0, minutes, seconds);
    }

}
