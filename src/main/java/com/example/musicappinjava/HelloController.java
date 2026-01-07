package com.example.musicappinjava;

import com.example.musicappinjava.domain.Song;
import com.example.musicappinjava.repository.SongRepository;
import com.example.musicappinjava.service.MusicService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HelloController {
    @FXML private TableView<Song> songTable;
    @FXML private TableColumn<Song, String> colId, colBand, colTitle, colGenre, colDuration;
    @FXML private ComboBox<String> genreCombo;
    @FXML private TextField addBandField, addTitleField, addGenreField, addDurationField;
    @FXML private TextField playlistNameField;
    @FXML private ComboBox<String> savedPlaylistCombo;

    private MusicService service;
    private ObservableList<Song> masterList;

    @FXML
    public void initialize(){
        service = new MusicService(new SongRepository());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colBand.setCellValueFactory(new PropertyValueFactory<>("band"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));

        refreshData();
        updatePlaylistNames();

        genreCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                songTable.setItems(masterList.filtered(s -> s.getGenre().equals(newVal)));
                savedPlaylistCombo.setValue(null);
                //savedPlaylistCombo.getSelectionModel().clearSelection();
            }
        });

        savedPlaylistCombo.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal != null) {
                List<Song> playlistSongs = service.loadPlaylist(newVal);
                songTable.setItems(FXCollections.observableArrayList(playlistSongs));
                genreCombo.setValue(null);
                //genreCombo.getSelectionModel().clearSelection();
            }
        });
    }

    private void refreshData() {
        List<Song> allSongs = service.getAllSongs();
        masterList = FXCollections.observableArrayList(allSongs);
        songTable.setItems(masterList);

        List<String> uniqueGenres = allSongs.stream()
                .map(Song::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        genreCombo.setItems(FXCollections.observableArrayList(uniqueGenres));
    }

    private void updatePlaylistNames(){
        List<String> names = service.getSavedPlaylistNames();
        savedPlaylistCombo.setItems(FXCollections.observableArrayList(names));
    }

   @FXML
   void onAddSongClick() {
        try{
            String band = addBandField.getText();
            String title = addTitleField.getText();
            String genre = addGenreField.getText();
            String duration = addDurationField.getText();
            service.addSong(band, title, genre, duration);

            refreshData();
            addBandField.clear();
            addTitleField.clear();
            addGenreField.clear();
            addDurationField.clear();

            new Alert(Alert.AlertType.INFORMATION, "Piesa a fost adaugata").show();
        } catch (Exception e){
           new Alert(Alert.AlertType.ERROR, "Eroare la adaugare: " + e.getMessage()).show();
        }
   }

   @FXML
    void onResetClick(){
        genreCombo.setValue(null);
        savedPlaylistCombo.setValue(null);
//        genreCombo.getSelectionModel().clearSelection();
//        savedPlaylistCombo.getSelectionModel().clearSelection();
        songTable.setItems(masterList);
   }

   @FXML
    void onGenerateClick(){
        String playlistName = playlistNameField.getText();
        if(playlistName == null || playlistName.trim().isEmpty()){
            new Alert(Alert.AlertType.WARNING, "Introduceti un nume pentru playlist").show();
            return;
        }
        try{
            List<Song> playlist = service.generatePlaylist(masterList);
            service.savePlaylist(playlistName, playlist);
            updatePlaylistNames();
            new Alert(Alert.AlertType.INFORMATION, "Playlist-ul " + playlistName + " a fost salvat").show();
            playlistNameField.clear();
        } catch (Exception e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
   }
}
