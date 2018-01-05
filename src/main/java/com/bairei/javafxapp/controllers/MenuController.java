package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class MenuController {
    private JavafxappApplication main;

    public void setMain(JavafxappApplication main) {
        this.main = main;
    }

    @FXML
    private void handleExit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Exit");
        alert.setHeaderText("You are about to exit from the application!");
        alert.setContentText("Are you sure you want to exit?");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

//    @FXML
//    private void handleSaveData(ActionEvent actionEvent){
//        saveAlbumData();
//    }

    @FXML
    private void handleAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("About");
        alert.setHeaderText("A simple musical bands crud application");
        alert.setContentText("Created using Spring Boot + JavaFX 8\nBy Błażej Kwapisz\ngithub: github.com/bairei");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
    }

//    public void saveAlbumData(){
//        File file = new File("albums.xml");
//        try {
//            JAXBContext jaxbContext = JAXBContext.newInstance(AlbumListWrapper.class);
//            Marshaller m = jaxbContext.createMarshaller();
//            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            AlbumListWrapper albumListWrapper = new AlbumListWrapper();
//            albumListWrapper.setAlbumList(albumRepository.findAll());
//            m.marshal(albumListWrapper, System.out);
//            m.marshal(albumListWrapper, file);
//        } catch (Exception e) {
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle("ERROR");
//            alert.setHeaderText("Could not save data");
//            alert.setContentText("could not save data to file" + file.getPath() + "\n" + e);
//            alert.showAndWait();
//        }
//    }
}

