package com.cesur.dam.data;

import com.cesur.dam.model.Dispositivo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DispositivoDAO implements DAO<Dispositivo> {

    private final DataSource dataSource;

    public DispositivoDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Dispositivo> listarTodos() {
        String sql = "SELECT id, nombre, categoria, disponible FROM Equipo";
        List<Dispositivo> registros = new ArrayList<>();

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                registros.add(new Dispositivo(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("categoria"),
                        rs.getBoolean("disponible")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al consultar dispositivos: " + e.getMessage(), e);
        }

        return registros;
    }

    @Override
    public Dispositivo insertar(Dispositivo entidad) {
        String sql = "INSERT INTO Equipo (nombre, categoria, disponible) VALUES (?, ?, ?)";

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getTipo());
            ps.setBoolean(3, entidad.getOperativo());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entidad.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar dispositivo: " + e.getMessage(), e);
        }
        return entidad;
    }
}