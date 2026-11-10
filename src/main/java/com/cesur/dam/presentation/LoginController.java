package com.cesur.dam.presentation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    public void login(ActionEvent actionEvent) {
        SceneNavigator.navigateTo("main-view.fxml", "Inventario - Listado de Registros");
    }
}