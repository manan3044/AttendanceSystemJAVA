package org.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;



import java.net.URL;
import java.util.ResourceBundle;

public class StudentLoggedIn implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private TableView<AttendanceData> attendanceTable;

    @FXML
    private TableColumn<AttendanceData, String> subName;

    @FXML
    private TableColumn<AttendanceData, Integer> classAttended;

    @FXML
    private Label totalClassConductedLabel;

    @FXML
    private Label totalClassAttendedLabel;

    @FXML
    private Label totalAttendancePercentLabel;

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

        DBUtils.populateTable(attendanceTable, subName, classAttended, totalClassAttendedLabel, totalClassConductedLabel, totalAttendancePercentLabel);
    }


    public void setUserInfo(String name)
    {
        welcomeLabel.setText(name);
    }

}

