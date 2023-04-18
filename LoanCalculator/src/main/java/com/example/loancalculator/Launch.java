package com.example.loancalculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launch extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader mainScreen = new FXMLLoader(Launch.class.getResource("visual.fxml"));
        Scene scene = new Scene(mainScreen.load(), 800, 700);
        stage.setTitle("Housing Loan Calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}