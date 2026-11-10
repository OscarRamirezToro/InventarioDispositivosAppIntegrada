package es.cesur.dam.navigation;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {

    private static Stage rootStage;
    private static final String BASE_VIEW_PATH = "/es/cesur/dam/views/";

    private WindowManager() {}

    public static void initStage(Stage stage) {
        rootStage = stage;
    }

    public static void changeScene(String fxmlView, String headerTitle) {
        if (rootStage == null) {
            throw new IllegalStateException("WindowManager no ha sido inicializado con un Stage principal.");
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(WindowManager.class.getResource(BASE_VIEW_PATH + fxmlView));
            Parent viewNode = fxmlLoader.load();
            Scene newScene = new Scene(viewNode);

            rootStage.setTitle(headerTitle);
            rootStage.setScene(newScene);
            rootStage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException("Fallo al cargar la escena FXML: " + fxmlView, e);
        }
    }
}