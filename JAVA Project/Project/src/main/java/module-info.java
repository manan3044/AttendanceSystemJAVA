module org.example.project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jBCrypt;


    opens org.example.project to javafx.fxml;
    exports org.example.project;
}