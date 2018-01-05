package com.bairei.javafxapp.controllers.utils;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.*;
import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.models.Band;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class UtilController {

    public static void returnToMain(Event event, JavafxappApplication main){
        try {
            FXMLLoader loader = new FXMLLoader(UtilController.class.getResource("/views/introView.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = main.getPrimaryStage();
//            Scene centerScene = new Scene(pane);
            BorderPane parentPane = (BorderPane)stage.getScene().getRoot();
            parentPane.setCenter(pane);
            IntroController controller = loader.getController();
            controller.setMain(main);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static boolean handleNewMember(Event event, JavafxappApplication main){
        try {
            FXMLLoader loader = new FXMLLoader(UtilController.class.getResource("/views/memberForm.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Member");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            MemberFormController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(stage);
            stage.showAndWait();
            return controller.isOkClicked();

        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public static void displayEmptyInput(Event event, JavafxappApplication main) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Empty input");
        alert.setHeaderText("Not all required text inputs are filled");
        alert.setContentText("Please make sure to check if you filled all the necessary inputs in!");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }

    public static void displayNFEAlert(Event event, JavafxappApplication main) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Number parsing exception");
        alert.setHeaderText("Unable to parse a string from one of the text fields into an Integer");
        alert.setContentText("Please provide an integer value into that field!");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }


    public static void handleBands(Event event, JavafxappApplication main, List<Band> bandList) {
        try {
            FXMLLoader loader = new FXMLLoader(main.getClass().getResource("/views/bandResults.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = main.getPrimaryStage();
            Scene centerScene = new Scene(pane);
            BorderPane parentPane = (BorderPane)stage.getScene().getRoot();
            parentPane.setCenter(pane);
            BandResultsController controller = loader.getController();
            controller.setMain(main);
            controller.handleBandList(bandList);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleAlbums(Event event, JavafxappApplication main, List<Album> albums) {
        try {
            FXMLLoader loader = new FXMLLoader(main.getClass().getResource("/views/albumResults.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = main.getPrimaryStage();
            Scene centerScene = new Scene(pane);
            BorderPane parentPane = (BorderPane)stage.getScene().getRoot();
            parentPane.setCenter(pane);
            AlbumResultsController controller = loader.getController();
            controller.setMain(main);
            controller.handleAlbumList(albums);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void displayIncorrectYearAlert(Event event, JavafxappApplication main) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Incorrect year inputs was given");
        alert.setHeaderText("Unable to search for bands where year 'from' is later than 'to'!");
        alert.setContentText("Your search query will be fixed, so the year from 'to' input will become the year 'from'," +
                " and vice versa!");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }

    public static void displayEmptyItemSelected(Event event, JavafxappApplication main) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("No selection");
        alert.setHeaderText("No item selected");
        alert.setContentText("Please select an item in the table");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }

    public static void handleAlbumForm(Event event, JavafxappApplication main, Album album) {
        try {
            FXMLLoader loader = new FXMLLoader(main.getClass().getResource("/views/albumForm.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Album form");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            AlbumFormController formController = loader.getController();
            formController.setAlbum(album);
            formController.setMain(main);
            formController.setDialogStage(stage);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleBandForm(Event event, JavafxappApplication main, Band band) {
        try {
            FXMLLoader loader = new FXMLLoader(main.getClass().getResource("/views/newBandForm.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Band");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            BandFormController formController = loader.getController();
            formController.setBand(band);
            formController.setDialogStage(stage);
            formController.setMain(main);
            stage.showAndWait();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void displayWrongDatesError(Event event, JavafxappApplication main, String errors) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(main.getPrimaryStage());
        alert.setTitle("Date input error");
        alert.setHeaderText("Wrong year input was given");
        alert.setContentText("Please correct errors from below:\n" + errors);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                main.getClass().getResource("/static/css/DarkTheme.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");

        alert.showAndWait();
    }
}
