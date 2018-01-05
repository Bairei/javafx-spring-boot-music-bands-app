package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.repositories.AlbumRepository;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Setter
@Getter
@Slf4j
public class IntroController {

    private JavafxappApplication main;
    private Stage dialogStage;

    @FXML
    private Button albumButton;
    @FXML
    private Button bandButton;

    public IntroController(){}

    @FXML
    private void handleSearchMenu(Event event) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/search.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Search");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            SearchController controller = loader.getController();
            controller.setMain(getMain());
            controller.setDialogStage(stage);
            if(event.getSource() == albumButton){
                controller.setOrigin(albumButton.getId());
            } else if (event.getSource() == bandButton){
                controller.setOrigin(bandButton.getId());
            } else {
                throw new Exception("Unknown event source!");
            }
            stage.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    private void handleNewBandForm(Event event){
        UtilController.handleBandForm(event, getMain(), null);
    }
    @FXML
    private void handleAlbumForm (Event event){
        UtilController.handleAlbumForm(event, getMain(), null);
    }
    @FXML
    private void handleAllBands(Event event){
        UtilController.handleBands(event, getMain(), null);
    }
    @FXML
    private void handleAllAlbums(Event event){
        UtilController.handleAlbums(event, getMain(), null);
    }

}
