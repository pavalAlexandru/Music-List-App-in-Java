module com.example.musicappinjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.musicappinjava to javafx.fxml;
    exports com.example.musicappinjava;
}