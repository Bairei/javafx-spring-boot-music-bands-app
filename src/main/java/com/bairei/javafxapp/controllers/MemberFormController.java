package com.bairei.javafxapp.controllers;

import com.bairei.javafxapp.JavafxappApplication;
import com.bairei.javafxapp.controllers.utils.UtilController;
import com.bairei.javafxapp.models.Member;
import com.bairei.javafxapp.repositories.MemberRepository;
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
public class MemberFormController {

    private JavafxappApplication main;
    private Stage dialogStage;

    private MemberRepository repository;

    @FXML
    private TextField nameTextField;

    private boolean isOkClicked = false;

    public MemberFormController() {
    }

    @Autowired
    private void setMemberRepository(MemberRepository repository){
        this.repository = repository;
    }

    @FXML
    private void handleNewMember(Event event){
        if(nameTextField.getText() != null && !nameTextField.getText().isEmpty()){
            Member member = new Member();
            member.setName(nameTextField.getText());
            repository.save(member);
            this.setOkClicked(true);
            closeForm(event);
        } else {
            UtilController.displayEmptyInput(event, getMain());
        }
    }

    @FXML
    private void closeForm(Event event) {
        dialogStage.close();
    }

}
