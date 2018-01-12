package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.models.Member;
import com.bairei.javafxapp.services.AlbumService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Getter
@Setter
@Slf4j
@Component
public class AlbumResultsController {

    private JavafxappApplication main;
    private ObservableList<Album> albums;

    private AlbumService albumService;

    @FXML
    private TableView<Album> albumTable;
    @FXML
    private TableColumn<Album, String> albumNameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label bandLabel;
    @FXML
    private Label yearRecordedLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label labelLabel;
    @FXML
    private ListView<Member> membersListView;

    public AlbumResultsController(){}

    @Autowired
    public void setAlbumService (AlbumService albumService){
        this.albumService = albumService;
    }

    @FXML
    private void initialize(){
        albumNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitle()));
        showAlbumDetails(null);
        albumTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showAlbumDetails(newValue));
        membersListView.setOrientation(Orientation.VERTICAL);
    }

    public void handleAlbumList(List<Album> albumList){
        if(albumList == null){
            albumList = albumService.findAll();
        }
        albums = FXCollections.observableArrayList();
        albums.addAll(albumList);
        albumTable.setItems(albums);
    }

    private void showAlbumDetails(Album album){
        if (album != null){
            nameLabel.setText(album.getTitle());
            yearRecordedLabel.setText(Integer.toString(album.getYearOfRelease()));
            bandLabel.setText(album.getBand().getName());
            genreLabel.setText(album.getGenre().getName());
            labelLabel.setText(album.getLabel().getName());
            membersListView.setItems(FXCollections.observableArrayList(album.getMembers()));
        }
    }

    @FXML
    private void handleDeleteButton(Event event) {
        Album selectedAlbum = albumTable.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null){
            albumService.delete(selectedAlbum);
            albumTable.getItems().remove(selectedAlbum);
        } else {
            // No item selected
            UtilController.displayEmptyItemSelected(event, getMain());
        }
    }

    @FXML
    private void handleEditButton(Event event){
        Album selectedAlbum = albumTable.getSelectionModel().getSelectedItem();
        if (selectedAlbum != null){
            UtilController.handleAlbumForm(event, getMain(), selectedAlbum);
            albumService.save(selectedAlbum);
            albumTable.refresh();
            showAlbumDetails(selectedAlbum);
        } else {
            // No item selected
            UtilController.displayEmptyItemSelected(event, getMain());
        }
    }

    @FXML
    private void returnToIntro(Event event){
        UtilController.returnToMain(event, getMain());
    }
}
