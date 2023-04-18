module com.example.loancalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.loancalculator to javafx.fxml;
    exports com.example.loancalculator;
    exports InputData;
    opens InputData to javafx.fxml;
}