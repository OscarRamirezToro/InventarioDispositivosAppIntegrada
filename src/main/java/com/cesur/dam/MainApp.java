package com.cesur.dam;

import com.cesur.dam.presentation.SceneNavigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneNavigator.setStage(primaryStage);
        SceneNavigator.navigateTo("login-view.fxml", "Inventario - Autenticación");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}