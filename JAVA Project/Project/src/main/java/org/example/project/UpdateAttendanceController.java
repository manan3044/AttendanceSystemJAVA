package org.example.project;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateAttendanceController implements Initializable {

    @FXML
    private Label title;

    @FXML
    private Label username;

    @FXML
    private TableView<UpdateTable> table;

    @FXML
    private TableColumn<UpdateTable,String> serial_column;

    @FXML
    private TableColumn<UpdateTable,String> name_column;

    @FXML
    private TableColumn<UpdateTable,String> attended_column;

    @FXML
    private TableColumn<UpdateTable,String> conducted_column;

    @FXML
    private VBox box;

    @FXML
    private Text name;

    @FXML
    private TextField name_field;

    @FXML
    private Text attendance;

    @FXML
    private TextField attendance_field;

    @FXML
    private Text conducted;

    @FXML
    private TextField conducted_field;

    @FXML
    private Button update;

    @FXML
    void rowClicked(MouseEvent event)
    {
        UpdateTable clickedData = table.getSelectionModel().getSelectedItem();
        name_field.setText(String.valueOf((clickedData.getName())));
        attendance_field.setText(String.valueOf((clickedData.getAttendance())));
        conducted_field.setText(String.valueOf((clickedData.getConducted())));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    update.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                DBUtils.update(event,name_field.getText(),attendance_field.getText(),conducted_field.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });
    }
}
