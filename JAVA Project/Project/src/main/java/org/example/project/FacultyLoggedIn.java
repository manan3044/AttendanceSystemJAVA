package org.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
public class FacultyLoggedIn implements Initializable{
    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addSubjectButton;

    @FXML
    private Button deleteSubjectButton;

    @FXML Button updateButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (logoutButton != null) {
            logoutButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DBUtils.logoutUser(actionEvent);
                }
            });
        }

        if (addSubjectButton != null) {
            addSubjectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DBUtils.changeScene(actionEvent, "add_subject.fxml", "", "");
                }
            });
        }

        if (deleteSubjectButton != null) {
            deleteSubjectButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    DBUtils.changeScene(actionEvent, "delete_subject.fxml", "", "");
                }
            });
        }
        if (updateButton != null) {
            updateButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
//                    DBUtils.changeScene(actionEvent, "delete_subject.fxml", "", "");
                    FXMLLoader loader = new FXMLLoader(FacultyLoggedIn.class.getResource("updateAttendance.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root, 1000, 630)); // add w and h
                    stage.show();
                }
            });
        }

    }


    public void setUserInfo(String name)
    {
        welcomeLabel.setText(name);
    }
}
