package es.cesur.dam.controllers;

import es.cesur.dam.config.DBConnection;
import es.cesur.dam.dao.DispositivoDAO;
import es.cesur.dam.dao.GenericDAO;
import es.cesur.dam.model.Dispositivo;
import es.cesur.dam.navigation.WindowManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateViewController implements Initializable {

    @FXML
    private TextField txtIdentificadorNombre;
    @FXML
    private ComboBox<String> comboFamilias;
    @FXML
    private CheckBox checkOperativo;

    private final GenericDAO<Dispositivo> dispositivoService = new DispositivoDAO(DBConnection.getDataSource());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboFamilias.setItems(FXCollections.observableArrayList(
                "Estación de Trabajo", "Portátil", "Periférico", "Servidor", "Redes"
        ));
        comboFamilias.getSelectionModel().selectFirst();
    }

    @FXML
    public void onGuardarRegistroClick(ActionEvent event) {
        String denominacion = txtIdentificadorNombre.getText();

        if (denominacion == null || denominacion.trim().isEmpty()) {
            desplegarNotificacion(Alert.AlertType.WARNING, "Validación requerida", "El nombre del dispositivo es obligatorio.");
            return;
        }

        Dispositivo nuevoRegistro = new Dispositivo();
        nuevoRegistro.setNombre(denominacion.trim());
        nuevoRegistro.setTipo(comboFamilias.getValue());
        nuevoRegistro.setOperativo(checkOperativo.isSelected());

        try {
            Dispositivo persistido = dispositivoService.insertar(nuevoRegistro);
            desplegarNotificacion(Alert.AlertType.INFORMATION, "Registro Almacenado",
                    "Dispositivo guardado con ID asignado: #" + persistido.getId());
            WindowManager.changeScene("main-view.fxml", "Catálogo de Dispositivos");
        } catch (Exception ex) {
            desplegarNotificacion(Alert.AlertType.ERROR, "Fallo de Inserción", "Error al guardar en la base de datos.");
        }
    }

    @FXML
    public void onCancelarClick(ActionEvent event) {
        WindowManager.changeScene("main-view.fxml", "Catálogo de Dispositivos");
    }

    private void desplegarNotificacion(Alert.AlertType tipo, String titulo, String cuerpo) {
        Alert cuadro = new Alert(tipo);
        cuadro.setHeaderText(titulo);
        cuadro.setContentText(cuerpo);
        cuadro.showAndWait();
    }
}