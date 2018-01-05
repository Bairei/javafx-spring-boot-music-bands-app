package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Label;
import com.bairei.javafxapp.repositories.LabelRepository;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class LabelFormController {
    private JavafxappApplication main;
    private Stage dialogStage;

    private LabelRepository repository;

    @FXML
    private TextField nameField;
    @FXML
    private TextField yearFoundedField;

    private boolean isOkClicked = false;

    public LabelFormController() {
    }

    @Autowired
    public void setLabelRepository(LabelRepository repository){
        this.repository = repository;
    }

    @FXML
    private void handleNewLabel(Event event){
        if(nameField.getText() != null && !nameField.getText().isEmpty() && yearFoundedField.getText() != null
                && !yearFoundedField.getText().isEmpty()){
            try {
                Label label = new Label();
                label.setName(nameField.getText());
                label.setYearFounded(Integer.parseInt(yearFoundedField.getText()));
                repository.save(label);
                setOkClicked(true);
                closeForm(event);

            } catch (NumberFormatException e){
                UtilController.displayNFEAlert(event, getMain());
            }
        } else {
            UtilController.displayEmptyInput(event, getMain());
        }
    }

    @FXML
    private void closeForm(Event event){
        dialogStage.close();
    }
}
