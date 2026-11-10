package com.cesur.dam.presentation;

import com.cesur.dam.config.DatabaseConfig;
import com.cesur.dam.data.DatabaseException;
import com.cesur.dam.data.DispositivoDAO;
import com.cesur.dam.data.DispositivoDaoImpl;
import com.cesur.dam.model.Dispositivo;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<Dispositivo> table;
    @FXML
    private TableColumn<Dispositivo, String> cId;
    @FXML
    private TableColumn<Dispositivo, String> cName;
    @FXML
    private TableColumn<Dispositivo, String> cCategory;
    @FXML
    private TableColumn<Dispositivo, String> cAvailability;

    // Dependemos de la interfaz (DIP) e inyectamos la conexión por constructor
    private final DispositivoDAO dispositivoDAO = new DispositivoDaoImpl(DatabaseConfig.getDataSource());
    private final ObservableList<Dispositivo> listaObservable = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarColumnas();
        cargarDatos();
    }

    private void configurarColumnas() {
        cId.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getId())));
        cName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNombre()));
        cCategory.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCategoria()));
        cAvailability.setCellValueFactory(cell -> {
            boolean disp = cell.getValue().getDisponible() != null && cell.getValue().getDisponible();
            return new SimpleStringProperty(disp ? "● Disponible" : "○ No disponible");
        });

        table.setItems(listaObservable);
    }

    private void cargarDatos() {
        try {
            listaObservable.clear();
            listaObservable.addAll(dispositivoDAO.findAll());
        } catch (DatabaseException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Datos", "No se pudo consultar la base de datos.");
        }
    }

    @FXML
    public void addNew(ActionEvent actionEvent) {
        SceneNavigator.navigateTo("details-view.fxml", "Inventario - Nuevo Registro");
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateTo("login-view.fxml", "Inventario - Autenticación");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}