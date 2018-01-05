package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Band;
import com.bairei.javafxapp.models.Genre;
import com.bairei.javafxapp.models.Member;
import com.bairei.javafxapp.repositories.BandRepository;
import com.bairei.javafxapp.repositories.LabelRepository;
import com.bairei.javafxapp.repositories.MemberRepository;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Component
@Getter
@Setter
@Slf4j
public class BandFormController {
    private JavafxappApplication main;
    private Stage dialogStage;

    private MemberRepository memberRepository;
    private LabelRepository labelRepository;
    private BandRepository bandRepository;

    @FXML
    private TextField nameField;
    @FXML
    private TextField yearFoundedField;
    @FXML
    private TextField yearDisbandedField;
    @FXML
    private ChoiceBox<Genre> genreChoiceBox;
    @FXML
    private ListView<Member> memberListView;

    private List<Member> members;
    private Band band;

    private boolean isOkClicked = false;

    @Autowired
    public void setBandRepository(BandRepository bandRepository){
        this.bandRepository = bandRepository;
    }

    @Autowired
    public void setLabelRepository(LabelRepository labelRepository){
        this.labelRepository = labelRepository;
    }

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    @FXML
    private void initialize(){
        genreChoiceBox.setItems(FXCollections.observableArrayList(Genre.values()));
        memberListView.setItems(FXCollections.observableArrayList(memberRepository.findAll()));
        memberListView.setOrientation(Orientation.VERTICAL);
        memberListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        memberListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                members = memberListView.getSelectionModel().getSelectedItems());
    }

    public void setBand(Band band){
        if (band != null){
            this.band = band;
            if(band.getName() != null && !band.getName().isEmpty()){
                nameField.setText(band.getName());
            }
            if(band.getYearFounded() != null){
                yearFoundedField.setText(String.valueOf(band.getYearFounded()));
            }
            if (band.getYearDisbanded() != null){
                yearDisbandedField.setText(String.valueOf(band.getYearDisbanded()));
            }
            if (band.getGenre() != null){
                genreChoiceBox.getSelectionModel().select(band.getGenre());
            }
            if (band.getMembers() != null && band.getMembers().size() > 0){
                for (Member m: band.getMembers()){
                    memberListView.getSelectionModel().select(m);
                }
            }
        }
    }

    @FXML
    private void handleNewBand(Event event){
        if(nameField.getText() != null && !nameField.getText().isEmpty() && !yearFoundedField.getText().isEmpty() &&
                yearFoundedField.getText() != null && genreChoiceBox.getValue() != null && members.size() > 0){
            if (getBand() == null) {
                band = new Band();
            }
            band.setName(nameField.getText());
            band.setGenre(genreChoiceBox.getValue());
            try {
                String errors = "";
                Integer founded = Integer.parseInt(yearFoundedField.getText());
                Integer disbanded = 0;
                if (founded > LocalDate.now().getYear() || founded < 1900) {
                    errors += "Wrong band founded input value!\n";
                }
                if (yearDisbandedField.getText() != null && !yearDisbandedField.getText().isEmpty()){
                    disbanded = Integer.parseInt(yearDisbandedField.getText());
                    if (disbanded < 1900 || disbanded > LocalDate.now().getYear()) {
                        errors += "Wrong band disbanded input value!\n";
                    }
                    if (founded > disbanded){
                        errors += "You cannot create a band that ceased to exist before it was formed!\n";
                    }
                }
                if (!errors.isEmpty()){
                    UtilController.displayWrongDatesError(event, getMain(), errors);
                    return;
                }
                band.setYearFounded(founded);
                if(yearDisbandedField.getText() != null && !yearDisbandedField.getText().isEmpty()){
                    band.setYearDisbanded(disbanded);
                }
            } catch (NumberFormatException e){
                UtilController.displayNFEAlert(event, getMain());
                return;
            }
            band.setMembers(new HashSet<>(members));
            bandRepository.save(band);
            // log.info(String.valueOf(bandRepository.findBandByName(band.getName())));
            returnToIntro(event);
        } else {
            UtilController.displayEmptyInput(event, getMain());
        }
    }

    @FXML
    private void handleNewMemberForm(Event event){
        boolean isNewMemberCreated = UtilController.handleNewMember(event, getMain());
        if(isNewMemberCreated){
            memberListView.setItems(FXCollections.observableArrayList(memberRepository.findAll()));
        }
    }

    @FXML
    private void returnToIntro(Event event) {
        dialogStage.close();
    }
}
