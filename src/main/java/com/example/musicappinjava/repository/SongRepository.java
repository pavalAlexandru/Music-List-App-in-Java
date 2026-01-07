package com.example.musicappinjava.repository;

import com.example.musicappinjava.domain.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongRepository {
    private String url = "jdbc:sqlite:music.db";

    public SongRepository(){
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()){
            stmt.execute("CREATE TABLE IF NOT EXISTS piese (" +
                    "id INTEGER PRIMARY KEY, " +
                    "formatie TEXT, " +
                    "titlu TEXT, " +
                    "gen_muzical TEXT, " +
                    "durata TEXT)");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Song> findAll(){
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM piese ORDER BY formatie ASC, titlu ASC";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()){
                songs.add(new Song(
                        rs.getInt("id"),
                        rs.getString("formatie"),
                        rs.getString("titlu"),
                        rs.getString("gen_muzical"),
                        rs.getString("durata")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }

        return songs;
    }

   public void savePlaylist(String tableName, List<Song> songs) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS " + tableName);
            stmt.execute("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER, formatie TEXT, titlu TEXT, gen_muzical TEXT, durata TEXT)");

            String insertSql = "INSERT INTO " + tableName + " VALUES(?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertSql);
            for (Song s : songs) {
                pstmt.setInt(1, s.getId());
                pstmt.setString(2, s.getBand());
                pstmt.setString(3, s.getTitle());
                pstmt.setString(4, s.getGenre());
                pstmt.setString(5, s.getDuration());
                pstmt.executeUpdate();
            }
        }
   }

   public void addSong(Song song) throws SQLException {
        String sql = "INSERT INTO piese (id, formatie, titlu, gen_muzical, durata) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, song.getId());
            pstmt.setString(2, song.getBand());
            pstmt.setString(3, song.getTitle());
            pstmt.setString(4, song.getGenre());
            pstmt.setString(5, song.getDuration());
            pstmt.executeUpdate();

        }
   }

   public List<String> getPlaylistNames() {
        List<String> names = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(url);
             ResultSet rs = conn.getMetaData().getTables(null, null, null, new String[]{"TABLE"})){
            while (rs.next()){
                String name = rs.getString("TABLE_NAME");
                if (!name.equalsIgnoreCase("piese") && !name.startsWith("sqlite_")) {
                    names.add(name);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return names;
   }

   public List<Song> getSongsFromPlaylist(String tableName) {
        List<Song> songs = new ArrayList<>();
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                songs.add(new Song(rs.getInt("id"), rs.getString("formatie"), rs.getString("titlu"), rs.getString("gen_muzical"), rs.getString("durata")));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
   }

}
