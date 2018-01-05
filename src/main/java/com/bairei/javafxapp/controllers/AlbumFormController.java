package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.*;
import com.bairei.javafxapp.models.Label;
import com.bairei.javafxapp.repositories.AlbumRepository;
import com.bairei.javafxapp.repositories.BandRepository;
import com.bairei.javafxapp.repositories.LabelRepository;
import com.bairei.javafxapp.repositories.MemberRepository;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
@Setter
@Getter
@Slf4j
public class AlbumFormController {

    private JavafxappApplication main;
    private Stage dialogStage;

    private BandRepository bandRepository;
    private AlbumRepository albumRepository;
    private MemberRepository memberRepository;
    private LabelRepository labelRepository;

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<Band> bandChoiceBox;
    @FXML
    private TextField yearField;
    @FXML
    private ChoiceBox<Label> labelChoiceBox;
    @FXML
    private ChoiceBox<Genre> genreChoiceBox;
    @FXML
    private ListView<Member> memberListView;

    private List<Member> members;

    private Album album;

    private boolean isOkClicked = false;

    public AlbumFormController() {
    }

    @Autowired
    public void setBandRepository(BandRepository bandRepository){
        this.bandRepository = bandRepository;
    }

    @Autowired
    public void setAlbumRepository(AlbumRepository albumRepository){
        this.albumRepository = albumRepository;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setLabelRepository(LabelRepository labelRepository){
        this.labelRepository = labelRepository;
    }

    public void setAlbum(Album album){
        if(album != null){
            this.album = album;
            if (album.getTitle() != null && !album.getTitle().isEmpty()) {
                nameField.setText(album.getTitle());
            }
            if (album.getBand() != null){
                // log.info(String.valueOf(bandChoiceBox.getItems().indexOf(album.getBand())));
                bandChoiceBox.getSelectionModel().select(bandChoiceBox.getItems().indexOf(album.getBand()));
            }
            if (album.getGenre() != null){
                // log.info("genre");
                genreChoiceBox.getSelectionModel().select(album.getGenre());
            }
            if (album.getYearOfRelease() != null){
                yearField.setText(String.valueOf(album.getYearOfRelease()));
            }
            if (album.getLabel() != null){
                // log.info("label");
                labelChoiceBox.getSelectionModel().select(album.getLabel());
            }
            if (album.getMembers() != null && album.getMembers().size() > 0){
                for (Member m: album.getMembers()){
                    memberListView.getSelectionModel().select(m);
                }
            }
        }
    }


    @FXML
    private void initialize(){
        bandChoiceBox.setItems(FXCollections.observableArrayList(bandRepository.findAll()));
        labelChoiceBox.setItems(FXCollections.observableArrayList(labelRepository.findAll()));
        genreChoiceBox.setItems(FXCollections.observableArrayList(Genre.values()));
        memberListView.setItems(FXCollections.observableArrayList(memberRepository.findAll()));
        memberListView.setOrientation(Orientation.VERTICAL);
        memberListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        memberListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            members = memberListView.getSelectionModel().getSelectedItems();
        });
    }

    @FXML
    private void handleNewAlbum(Event event){
        if(bandChoiceBox.getValue() != null && labelChoiceBox.getValue() != null && genreChoiceBox.getValue() != null
                && nameField.getText() != null && !nameField.getText().isEmpty() && yearField.getText() != null
                && !yearField.getText().isEmpty() && members.size() > 0){
            if (getAlbum() == null) {
                log.info("new album");
                album = new Album();
            }
            album.setBand(bandChoiceBox.getValue());
            album.setGenre(genreChoiceBox.getValue());
            album.setLabel(labelChoiceBox.getValue());
            album.setTitle(nameField.getText());
            try {
                String errors = "";
                Integer released = Integer.valueOf(yearField.getText());
                if (released > LocalDate.now().getYear() || released < 1900 || released < album.getBand().getYearFounded()){
                    errors += "Wrong year of release!\n";
                }
                if (!errors.isEmpty()){
                    UtilController.displayWrongDatesError(event, getMain(), errors);
                    return;
                }
                album.setYearOfRelease(Integer.valueOf(yearField.getText()));
            } catch (NumberFormatException e){
                UtilController.displayNFEAlert(event, getMain());
                return;
            }
            album.setMembers(new HashSet<>(members));
            albumRepository.save(album);
            //log.info(albumRepository.findAll().toString());
            //log.info(album.getTitle() + " " + album.getBand().getName());
            bandRepository.save(album.getBand());
            returnToIntro(event);
        } else {
            UtilController.displayEmptyInput(event, getMain());
        }
    }

    @FXML
    private void handleNewMemberForm(Event event){
        boolean isNewMemberCreated = UtilController.handleNewMember(event, getMain());
        if(isNewMemberCreated){
            log.info("OK clicked!");
            memberListView.setItems(FXCollections.observableArrayList(memberRepository.findAll()));
        } else {
            log.info("No ok clicked :(");
        }
    }

    @FXML
    private void handleNewLabelForm(Event event){
        try {
            FXMLLoader loader = new FXMLLoader(UtilController.class.getResource("/views/labelForm.fxml"));
            loader.setControllerFactory(main.getContext()::getBean);
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            stage.setTitle("New Member");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(main.getPrimaryStage());
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            LabelFormController controller = loader.getController();
            controller.setMain(main);
            controller.setDialogStage(stage);
            stage.showAndWait();
            if(controller.isOkClicked()){
                log.info("OK clicked!!");
                labelChoiceBox.setItems(FXCollections.observableArrayList(labelRepository.findAll()));
            } else {
                log.info("No ok clicked :(");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToIntro(Event event){
        dialogStage.close();
    }
}
