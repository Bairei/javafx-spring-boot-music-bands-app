package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Album;
import com.bairei.javafxapp.models.Band;
import com.bairei.javafxapp.repositories.AlbumRepository;
import com.bairei.javafxapp.repositories.BandRepository;
import com.bairei.javafxapp.services.AlbumService;
import com.bairei.javafxapp.services.BandService;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Getter
@Setter
@Slf4j
public class SearchController {
    private JavafxappApplication main;
    private Stage dialogStage;
    private String origin;

    private AlbumService albumService;
    private BandService bandService;

    @FXML
    private TextField phraseInput;
    @FXML
    private TextField fromYearInput;
    @FXML
    private TextField toYearInput;

    public SearchController(){}

    @Autowired
    public void setAlbumRepository(AlbumService albumService){
        this.albumService = albumService;
    }

    @Autowired
    public void setBandRepository(BandService bandService){
        this.bandService = bandService;
    }

    @FXML
    private void handleSearch(Event event){
        String phrase = "";
        if(phraseInput.getText() != null && !phraseInput.getText().isEmpty()){
            phrase = phraseInput.getText();
        }
        Integer from = 1900;
        Integer to = LocalDate.now().getYear();
        try {
            if(fromYearInput.getText() != null && !fromYearInput.getText().isEmpty()){

                from = Integer.parseInt(fromYearInput.getText());
            }
            if(fromYearInput.getText() != null && !fromYearInput.getText().isEmpty()){
                to = Integer.parseInt(toYearInput.getText());
            }
            if(from > to){
                UtilController.displayIncorrectYearAlert(event, getMain());
                Integer help = from;
                from = to;
                to = help;
            }
        } catch (NumberFormatException e){
            UtilController.displayNFEAlert(event, getMain());
            return;
        }
        log.info(from + " " + to);
        List<Album> albums = null;
        List<Band> bands = null;
        if(!phrase.isEmpty()){
            if (origin.equals("albumButton")){
                albums = albumService.findAlbumsByTitleContainingIgnoreCaseAndYearOfReleaseBetween(phrase, from, to);
                log.info(String.valueOf(albums.size()));
            } else {
                bands = bandService.findBandsByNameContainingIgnoreCaseAndYearFoundedBetween(phrase, from, to);
            }
        } else {
            log.info("empty");
            if(origin.equals("albumButton")){
                albums = albumService.findAlbumsByYearOfReleaseBetween(from, to);
            } else {
                bands = bandService.findBandsByYearFoundedBetween(from, to);
            }
        }
        if(albums != null){
            UtilController.handleAlbums(event, getMain(), albums);
        } else {
            UtilController.handleBands(event, getMain(), bands);
        }
        returnToIntro(event);
    }

    @FXML
    private void returnToIntro(Event event){
        dialogStage.close();
    }
}
