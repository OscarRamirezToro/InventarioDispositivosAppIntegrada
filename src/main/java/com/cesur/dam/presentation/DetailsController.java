package com.cesur.dam.presentation;

import com.cesur.dam.config.DatabaseConfig;
import com.cesur.dam.data.DatabaseException;
import com.cesur.dam.data.DispositivoDAO;
import com.cesur.dam.data.DispositivoDaoImpl;
import com.cesur.dam.model.Dispositivo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DetailsController implements Initializable {

    @FXML
    private Spinner<Integer> spinnerId;
    @FXML
    private TextField txtName;
    @FXML
    private ChoiceBox<String> choiceCategory;
    @FXML
    private CheckBox chkAvailable;

    private final DispositivoDAO dispositivoDAO = new DispositivoDaoImpl(DatabaseConfig.getDataSource());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceCategory.setItems(FXCollections.observableArrayList(
                "Portátil", "Sobremesa", "Monitor", "Teclado", "Impresora"
        ));
        choiceCategory.getSelectionModel().selectFirst();
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        String nombre = txtName.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", "El campo nombre no puede quedar vacío.");
            return;
        }

        Dispositivo nuevo = new Dispositivo();
        nuevo.setNombre(nombre.trim());
        nuevo.setCategoria(choiceCategory.getValue());
        nuevo.setDisponible(chkAvailable.isSelected());

        try {
            Dispositivo guardado = dispositivoDAO.save(nuevo);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Dispositivo guardado correctamente con ID: " + guardado.getId());
            SceneNavigator.navigateTo("main-view.fxml", "Inventario - Listado de Registros");
        } catch (DatabaseException ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Fallo de Persistencia", "No se pudo insertar el registro en MySQL.");
        }
    }

    @FXML
    public void goBack(ActionEvent actionEvent) {
        SceneNavigator.navigateTo("main-view.fxml", "Inventario - Listado de Registros");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}