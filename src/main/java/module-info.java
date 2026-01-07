module com.example.musicappinjava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.musicappinjava to javafx.fxml;
    opens com.example.musicappinjava.domain to javafx.base;
    exports com.example.musicappinjava;
}