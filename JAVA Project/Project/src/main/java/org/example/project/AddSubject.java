package org.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddSubject implements Initializable {
    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private TextField subjectNameField;

    @FXML
    private Button submitButton;
    @FXML
    private Button cancelButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"AIML-A", "AIML-B"};
        classComboBox.getItems().addAll(items);

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DBUtils.addSubject(actionEvent,501 ,subjectNameField.getText(), classComboBox.getValue());
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
