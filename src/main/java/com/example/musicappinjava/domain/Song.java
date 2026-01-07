package com.example.musicappinjava.domain;

import java.time.LocalTime;

public class Song{
    private int id;
    private String band;
    private String title;
    private String genre;
    private String duration;

    public Song(int id, String band, String title, String genre, String duration){
        this.id = id;
        this.band = band;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
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

    public String getDuration() {
        return duration;
    }

    public int getDurationInSecond(){
        String[] parts = duration.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString(){
        return  "ID: " + id + "\n" +
                "Band: " + band + "\n" +
                "Title: " + title + "\n" +
                "Genre: " + genre + "\n" +
                "Duration: " + duration;
    }

}
