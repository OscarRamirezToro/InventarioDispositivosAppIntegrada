package com.cesur.dam.controllers;

import com.cesur.dam.data.DispositivoDAO;
import com.cesur.dam.data.DAO;
import com.cesur.dam.model.Dispositivo;
import com.cesur.dam.navigation.WindowManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private TableView<Dispositivo> tablaDispositivos;
    @FXML
    private TableColumn<Dispositivo, String> colId;
    @FXML
    private TableColumn<Dispositivo, String> colNombre;
    @FXML
    private TableColumn<Dispositivo, String> colTipo;
    @FXML
    private TableColumn<Dispositivo, String> colEstado;

    private final DAO<Dispositivo> dispositivoService = new DispositivoDAO(DBConnection.getDataSource());
    private final ObservableList<Dispositivo> masterData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        asociarColumnas();
        recuperarRegistros();
    }

    private void asociarColumnas() {
        colId.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        colNombre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipo()));
        colEstado.setCellValueFactory(cell -> {
            boolean op = cell.getValue().getOperativo() != null && cell.getValue().getOperativo();
            return new SimpleStringProperty(op ? "Disponible" : "En Mantenimiento");
        });

        tablaDispositivos.setItems(masterData);
    }

    private void recuperarRegistros() {
        masterData.clear();
        masterData.addAll(dispositivoService.listarTodos());
    }

    @FXML
    public void onCrearNuevoClick(ActionEvent event) {
        WindowManager.changeScene("create-view.fxml", "Alta de Nuevo Dispositivo");
    }

    @FXML
    public void onCerrarSesionClick(ActionEvent event) {
        WindowManager.changeScene("login-view.fxml", "Autenticación de Usuario");
    }
}