package org.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteSubject implements Initializable {
    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> subjects = DBUtils.retriveSubjects(null, 501);
        subjectComboBox.getItems().addAll(subjects);

        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String selectedSubject = subjectComboBox.getValue();
                String subjectName = selectedSubject.split(" \\(")[0];
                DBUtils.deleteSubject(actionEvent,501 ,subjectName);
            }
        });

        if (cancelButton != null) {
            cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DBUtils.cancel(actionEvent);
                }
            });
        }

    }
}
