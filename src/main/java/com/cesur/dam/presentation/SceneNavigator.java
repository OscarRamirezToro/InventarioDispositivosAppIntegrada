package com.cesur.dam.presentation;

import com.cesur.dam.MainApp;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneNavigator {

    private static final String BASE_PATH = "/com/cesur/dam/views/";
    private static Stage mainStage;

    private SceneNavigator() {
    }

    public static void setStage(Stage stage) {
        mainStage = stage;
    }

    public static void navigateTo(String fxmlFile, String windowTitle) {
        if (mainStage == null) {
            throw new IllegalStateException("El Stage no ha sido configurado en SceneNavigator.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(BASE_PATH + fxmlFile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            mainStage.setTitle(windowTitle);
            mainStage.setScene(scene);
            mainStage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar la vista FXML: " + fxmlFile, e);
        }
    }
}