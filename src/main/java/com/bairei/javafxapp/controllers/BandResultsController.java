package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.models.Band;
import com.bairei.javafxapp.models.Member;
import com.bairei.javafxapp.services.BandService;
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

import java.util.ArrayList;
import java.util.List;

@Component
@Setter
@Getter
@Slf4j
public class BandResultsController {

    private JavafxappApplication main;
    private ObservableList<Band> bands;

    private BandService bandService;

    @FXML
    private TableView<Band> bandTable;
    @FXML
    private TableColumn<Band, String> bandNameColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label yearFoundedLabel;
    @FXML
    private Label yearDisbandedLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private ListView<Member> membersListView;
    @FXML
    private ListView<Album> albumsListView;

    @Autowired
    public void setBandService (BandService bandService){
        this.bandService = bandService;
    }

    public BandResultsController(){
    }

    @FXML
    private void initialize(){
        bandNameColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getName()));
        bandTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showBandDetails(newValue));
        membersListView.setOrientation(Orientation.VERTICAL);
        albumsListView.setOrientation(Orientation.VERTICAL);
    }

    public void handleBandList(List<Band> bandList){
        if(bandList == null) {
            bandList = bandService.findAll();
        }
        bands = FXCollections.observableArrayList(bandList);
        bandTable.setItems(bands);
        log.info(bandList.toString());
    }

    private void showBandDetails(Band band) {
        if (band != null){
            nameLabel.setText(band.getName());
            yearFoundedLabel.setText(Integer.toString(band.getYearFounded()));
            if(band.getYearDisbanded() == null || band.getYearDisbanded() < 0){
                yearDisbandedLabel.setText("---");
            } else {
                yearDisbandedLabel.setText(Integer.toString(band.getYearDisbanded()));
            }
            genreLabel.setText(band.getGenre().getName());
            membersListView.setItems(FXCollections.observableArrayList(band.getMembers()));
            List<Album> albums = new ArrayList<>(band.getAlbums());
            albumsListView.setItems(FXCollections.observableArrayList(albums));
        }
    }

    @FXML
    private void handleDeleteButton(Event event){
        Band selectedBand = bandTable.getSelectionModel().getSelectedItem();
        if (selectedBand != null){
            bandTable.getItems().remove(selectedBand);
            bandService.delete(selectedBand);
        } else {
            // Nothing was selected
            UtilController.displayEmptyItemSelected(event, getMain());
        }
    }

    @FXML
    private void handleEditButton(Event event){
        Band selectedBand = bandTable.getSelectionModel().getSelectedItem();
        if (selectedBand != null){
            UtilController.handleBandForm(event, getMain(), selectedBand);
            bandService.save(selectedBand);
            bandTable.refresh();
            showBandDetails(selectedBand);
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
