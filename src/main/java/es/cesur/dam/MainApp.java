package es.cesur.dam;

import es.cesur.dam.navigation.WindowManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        WindowManager.initStage(primaryStage);
        WindowManager.changeScene("login-view.fxml", "Autenticación de Usuario");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}