package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.services.AlbumService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class MenuController {
    private JavafxappApplication main;
    private AlbumService albumService;

    @Autowired
    public void setAlbumService(AlbumService albumService){
        this.albumService = albumService;
    }

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

    @FXML
    private void handleAbout(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("About");
        alert.setHeaderText("A simple musical bands crud application");
        alert.setContentText("Created using Spring Boot + JavaFX 8\nBy Błażej Kwapisz");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        alert.showAndWait();
    }

    @FXML
    private void handleStatistics(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader(main.getClass().getResource("/views/genreStatistics.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Genre Statistics");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            dialogStage.setScene(scene);

            // Set the albums into the controller.
            GenreStatisticsController controller = loader.getController();
            controller.setAlbumData(albumService.findAll());

            dialogStage.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}

