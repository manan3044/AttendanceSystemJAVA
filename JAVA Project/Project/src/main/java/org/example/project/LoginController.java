package org.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ComboBox<String> categorycomboBox;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] items = {"Faculty", "Student"};
        categorycomboBox.getItems().addAll(items);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(usernameField.getText()+passwordField.getText()+ categorycomboBox.getValue());
//                Long username = Long.parseLong(usernameField.getText());
//                System.out.println((username));
                DBUtils.loginUser(actionEvent, usernameField.getText(), passwordField.getText(), categorycomboBox.getValue());
            }
        });
    }
}