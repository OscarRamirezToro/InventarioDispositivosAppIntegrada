package es.cesur.dam.controllers;

import es.cesur.dam.navigation.WindowManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginViewController {

    @FXML
    public void onLoginClick(ActionEvent event) {
        WindowManager.changeScene("main-view.fxml", "Catálogo de Dispositivos");
    }
}