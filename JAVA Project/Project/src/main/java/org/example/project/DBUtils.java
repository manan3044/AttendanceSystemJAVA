package org.example.project;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String title, String name) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();
            if (loader.getController() instanceof StudentLoggedIn) {
                StudentLoggedIn studentLoggedIn = loader.getController();
                studentLoggedIn.setUserInfo(name);
            }
            if (loader.getController() instanceof FacultyLoggedIn) {
                FacultyLoggedIn facultyLoggedIn = loader.getController();
                facultyLoggedIn.setUserInfo(name);
            }
            Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());
            stage.setTitle(title);
            stage.setScene(new Scene(root, 900, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loginUser(ActionEvent event, String username, String password, String category) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM USERDATA WHERE username=? AND password=? AND category=?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, category);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String fxmlFileName = category.toLowerCase() + "_login.fxml";
                changeScene(event, fxmlFileName, "Welcome", username);
            } else {
                System.out.println("Password did not match");
                // You might want to display an error message to the user here.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors gracefully, e.g., display a user-friendly error message.
        }
    }

    public static void logoutUser(ActionEvent event) {
        changeScene(event, "login.fxml", "", "");
    }


    public static void addSubject(ActionEvent event,int facultyID ,String subName, String div){
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO TEACHINGLIST VALUES(?, ?, ?)")) {
            stmt.setInt(1, facultyID);
            stmt.setString(2, subName);
            stmt.setString(3, div);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A new row has been inserted.");
            }
            String facultyID2 = String.valueOf(facultyID);
            changeScene(event, "faculty_login.fxml", "", facultyID2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSubject(ActionEvent event,int facultyID ,String subName){
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM TEACHINGLIST WHERE FACULTY_ID = ? and SUBJECT_NAME = ?")) {
            stmt.setInt(1, facultyID);
            stmt.setString(2, subName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("A new row has been inserted.");
            }
            cancel(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> retriveSubjects(ActionEvent event, int facultyID)
    {
        List<String> subjectList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
             PreparedStatement stmt = conn.prepareStatement("SELECT CONCAT(SUBJECT_NAME, ' (', DIVI, ')') AS SUBJECT_DIV FROM TEACHINGLIST WHERE FACULTY_ID = ?")) {
            stmt.setInt(1, facultyID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String subjectDiv = rs.getString("SUBJECT_DIV");
                subjectList.add(subjectDiv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subjectList;
    }

    public static void cancel(ActionEvent event) {
        changeScene(event, "faculty_login.fxml", "", "501");
    }

    public static void populateTable(TableView<AttendanceData> tableView,
                                     TableColumn<AttendanceData, String> subjectNameColumn,
                                     TableColumn<AttendanceData, Integer> classAttendedColumn, Label totalClassAttendedLabel, Label totalClassConductedLabel, Label totalAttendancePercentLabel) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044")) {
            String prnToSearch = "22070126062";
            String sql = "SELECT DIVI FROM STUDENT_LIST WHERE PRN = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, prnToSearch);
            String div = "";
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                div = rs.getString("DIVI");
            }

            String sql2 = "SELECT TABLE_NAME FROM CLASSES WHERE CLASS_NAME = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setString(1, div);
            String tableName = "";
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                tableName = rs2.getString("TABLE_NAME");
            }

            String query = "SELECT * FROM " + tableName + "_ATTENDANCE WHERE PRN = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, prnToSearch);

            ResultSet rs3 = stmt.executeQuery();

            String query_get = "SELECT CLASS_CONDUCTED FROM TEACHINGLIST WHERE Divi = ?";
            PreparedStatement ps_get = conn.prepareStatement(query_get);
            ps_get.setString(1, div);
            ResultSet rs_get = ps_get.executeQuery();
            int class_conducted = 0;
            if (rs_get.next()) {
                class_conducted = rs_get.getInt("CLASS_CONDUCTED");
            }

            int totalAttendance = 0;

            tableView.getItems().clear();

            // Populate data into TableColumn objects
            subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
            classAttendedColumn.setCellValueFactory(new PropertyValueFactory<>("classAttended"));

            while (rs3.next()) {
                ResultSetMetaData rsmd = rs3.getMetaData();
                int columnCount = rsmd.getColumnCount();

                for (int i = 3; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    int attendance = rs3.getInt(columnName);
                    totalAttendance += attendance;
                    AttendanceData attendanceData = new AttendanceData(
                            columnName,
                            attendance
                    );
                    tableView.getItems().add(attendanceData);
                }
            }

            System.out.println("\nTotal Classes Attended: " + totalAttendance);
            System.out.println("Total Classes Conducted: " + class_conducted);
            totalClassAttendedLabel.setText(String.valueOf(totalAttendance));
            totalClassConductedLabel.setText(String.valueOf(class_conducted));
            double attendancePercent = (totalAttendance / (double) class_conducted) * 100;
            if (attendancePercent > 100) {
                attendancePercent = 100;
            }
            String formattedAttendancePercent = String.format("%.2f", attendancePercent);
            System.out.println("Total Attendance Percent: " + formattedAttendancePercent + "%");
            totalAttendancePercentLabel.setText(String.valueOf(attendancePercent)+"%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void update(ActionEvent event, String name, String attendance, String conducted) throws SQLException, IOException {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement insert =null;
        ResultSet resultSet =null;
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/AttendanceManagement", "root", "manan@$3044");
        stmt = conn.prepareStatement("SELECT name FROM aiml_a_2022");
        stmt.setString(1, name);
        resultSet = stmt.executeQuery();

        insert = conn.prepareStatement("INSERT INTO table (name) VALUES(?)");
        insert.setString(1, name);
//        insert.setString(2, attendance);
//        insert.setString(3, conducted);

        insert.executeUpdate();

        FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource("updateAttendance.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 630)); // add w and h
        stage.show();

    }

}
